package com.example.brandonderbidge.myapplication.sell;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.brandonderbidge.myapplication.model.Contract;
import com.example.brandonderbidge.myapplication.model.FilterModel;
import com.example.brandonderbidge.myapplication.main.MainController;
import com.example.brandonderbidge.myapplication.R;
import com.example.brandonderbidge.myapplication.buy.FilterDialog;
import com.example.brandonderbidge.myapplication.model.MyData;

import java.util.ArrayList;

/**
 * Created by justinbrunner on 10/15/17.
 */

public class SellFragment extends Fragment {
    private String TAG = "SellFragment";
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    FloatingActionButton fabNew;
    private SellAdapter adapter;
    private MainController mainController;
    private static ArrayList<Contract> listOfContracts;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell, container, false);
        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.sell_recycler_view);
        fabNew = view.findViewById(R.id.create_new_contract);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new SellAdapter(listOfContracts);
        recyclerView.setAdapter(adapter);

        loadSellContracts();
        getActivity().setTitle(R.string.sell_contracts);

        fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewContractFragment  newContractFragment = new NewContractFragment();
                newContractFragment.setMainController(mainController);

                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.activity_main_fragment_container, newContractFragment)
                        .addToBackStack(getString(R.string.TAG_new_contract)).commit();
            }
        });

        return view;
    }

    public void loadSellContracts() {
        listOfContracts = new ArrayList<>();

        for (int i = 0; i < MyData.sellContracts.length; i++) {
            Contract contract = MyData.sellContracts[i];

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

    public void showDialog() {
        Log.v(TAG, "Showing Dialog");

        if (getView() != null && getView().findViewById(R.id.filter_layout) == null) {
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
