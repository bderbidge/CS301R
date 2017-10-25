package com.example.brandonderbidge.myapplication.buy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.brandonderbidge.myapplication.main.MainController;
import com.example.brandonderbidge.myapplication.model.Contract;
import com.example.brandonderbidge.myapplication.model.FilterModel;
import com.example.brandonderbidge.myapplication.model.Model;
import com.example.brandonderbidge.myapplication.model.MyData;
import com.example.brandonderbidge.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by justinbrunner on 10/15/17.
 */

public class BuyFragment extends Fragment {
    private String TAG = "BuyFragment";
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private static CustomAdapter adapter;
    private MainController mainController;
    private static ArrayList<Contract> listOfContracts;
    private StorageReference mStorageRef;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Apartments");
    DatabaseReference userRef = database.getReference("Users");


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        mStorageRef = FirebaseStorage.getInstance().getReference();
        View view = inflater.inflate(R.layout.fragment_buy, container, false);
        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.buy_recycler_view);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new CustomAdapter(listOfContracts);
        recyclerView.setAdapter(adapter);

        getActivity().setTitle(R.string.buy_contracts);

        loadContracts();
        // Attach a listener to read the data at our posts reference
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Model.instance().getAllContracts().clear();

                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    Contract contract = childSnapshot.getValue(Contract.class);
                    System.out.println(contract);
                    Model.instance().getAllContracts().put(contract.getID(), contract);

                }
                loadContracts();

                System.out.println(dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return view;
    }

    public void loadContracts() {
        listOfContracts = new ArrayList<>();

        Log.v(TAG, "Sex Filter: " + FilterModel.getInstance().getSex());
        Log.v(TAG, "Price High Filter: " + FilterModel.getInstance().getPriceHigh());
        Log.v(TAG, "Price Low Filter: " + FilterModel.getInstance().getPriceLow());
        Log.v(TAG, "Marital Status Filter: " + FilterModel.getInstance().getMaritalStatus());

        for (Contract value : Model.instance().getAllContracts().values()) {
            Contract contract = value;

            if (FilterModel.getInstance().getMaritalStatus() != null) {
                if (!contract.getMaritalStatus().equalsIgnoreCase(FilterModel.getInstance().getMaritalStatus())) {
                    continue;
                } else if (FilterModel.getInstance().getSex() != null
                        && contract.getSex() != null
                        && !contract.getSex().equalsIgnoreCase(FilterModel.getInstance().getSex())) {
                    continue;
                }
            }
            if (FilterModel.getInstance().getPriceLow() != null
                    && contract.getPrice() < FilterModel.getInstance().getPriceLow()) {
                continue;
            } else if (FilterModel.getInstance().getPriceHigh() != null
                    && contract.getPrice() > FilterModel.getInstance().getPriceHigh()) {
                continue;
            }



            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            Date sellByDate;
            try {
                sellByDate = df.parse(contract.getSellBy());
                Date today = new Date();
                if(sellByDate.after(today)){
                    System.out.println(contract);
                    listOfContracts.add(contract);
                }
                else {
                    System.out.println(contract);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }



        }

        adapter.setDataSet(listOfContracts);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.buy_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.filter_item:
                showDialog();
                break;
            default:

        }

        return true;
    }

    public void showDialog() {
        Log.v(TAG, "Showing Dialog");

        if (getActivity().findViewById(R.id.filter_layout) == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FilterDialog newFragment = new FilterDialog();

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction.add(android.R.id.content, newFragment)
                    .commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        loadContracts();
    }
}
