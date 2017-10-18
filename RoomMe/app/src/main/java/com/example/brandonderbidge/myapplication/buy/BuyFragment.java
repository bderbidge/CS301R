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
import com.example.brandonderbidge.myapplication.model.MyData;
import com.example.brandonderbidge.myapplication.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;

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

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

        loadContracts();
        getActivity().setTitle(R.string.buy_contracts);



        return view;
    }

    public void loadContracts() {
        listOfContracts = new ArrayList<>();

        Log.v(TAG, "Sex Filter: " + FilterModel.getInstance().getSex());
        Log.v(TAG, "Price High Filter: " + FilterModel.getInstance().getPriceHigh());
        Log.v(TAG, "Price Low Filter: " + FilterModel.getInstance().getPriceLow());
        Log.v(TAG, "Marital Status Filter: " + FilterModel.getInstance().getMaritalStatus());

        for (int i = 0; i < MyData.nameArray.length; i++) {
            Contract contract = MyData.contracts[i];

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


            listOfContracts.add(MyData.contracts[i]);
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
}
