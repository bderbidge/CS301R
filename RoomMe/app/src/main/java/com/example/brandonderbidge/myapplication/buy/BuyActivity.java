package com.example.brandonderbidge.myapplication.buy;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.brandonderbidge.myapplication.BottomNavigationViewHelper;
import com.example.brandonderbidge.myapplication.Contract;
import com.example.brandonderbidge.myapplication.FilterModel;
import com.example.brandonderbidge.myapplication.R;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.Map;

public class BuyActivity extends AppCompatActivity {
    private final String TAG = "BuyActivity";
    private static CustomAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Contract> listOfContracts;
    static View.OnClickListener myOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        Iconify.with(new FontAwesomeModule());


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new CustomAdapter(listOfContracts);
        recyclerView.setAdapter(adapter);

        changeNavItemSelected("buy");

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.navigation_buy:
                                createToast("Buy Page Selected", Toast.LENGTH_SHORT);
                                changeNavItemSelected("buy");
                                break;
                            case R.id.navigation_sell:
                                createToast("Sell Page Selected", Toast.LENGTH_SHORT);
                                changeNavItemSelected("sell");
                                break;
                            case R.id.navigation_messages:
                                createToast("Messages Page Selected", Toast.LENGTH_SHORT);
                                changeNavItemSelected("messages");
                                break;
                            case R.id.navigation_more:
                                createToast("More Page Selected", Toast.LENGTH_SHORT);
                                changeNavItemSelected("more");
                        }
                        return true;
                    }
                });


        loadFakeData();
    }


    public void loadFakeData() {
        listOfContracts = new ArrayList<>();

        Log.e(TAG, "Sex Filter: " + FilterModel.getInstance().getSex());
        Log.e(TAG, "Price High Filter: " + FilterModel.getInstance().getPriceHigh());
        Log.e(TAG, "Price Low Filter: " + FilterModel.getInstance().getPriceLow());
        Log.e(TAG, "Marital Status Filter: " + FilterModel.getInstance().getMaritalStatus());

        for (int i = 0; i < MyData.nameArray.length; i++) {
            Contract contract = MyData.contracts[i];
            if (FilterModel.getInstance().getSex() != null
                    && contract.getSex() != null
                    && !contract.getSex().equalsIgnoreCase(FilterModel.getInstance().getSex())) {
                continue;
            } else if (FilterModel.getInstance().getPriceLow() != null
                    && contract.getPrice() < FilterModel.getInstance().getPriceLow()) {
                continue;
            } else if (FilterModel.getInstance().getPriceHigh() != null
                    && contract.getPrice() > FilterModel.getInstance().getPriceHigh()) {
                continue;
            } else if (FilterModel.getInstance().getMaritalStatus() != null
                    && contract.getMaritalStatus() != null
                    && !contract.getMaritalStatus().equalsIgnoreCase(FilterModel.getInstance().getMaritalStatus())) {
                continue;
            }


            listOfContracts.add(MyData.contracts[i]);
        }

        adapter.setDataSet(listOfContracts);
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.buy_menu, menu);

        menu.findItem(R.id.filter_item)
            .setIcon(new IconDrawable(getBaseContext(), FontAwesomeIcons.fa_filter)
            .colorRes(R.color.White)
            .actionBarSize());

        return true;
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


    public void createToast(String message, int toastLength) {
        Toast.makeText(getBaseContext(), message, toastLength).show();
    }

    public void showProgressWheel(boolean show) {
        /*if (findViewById(R.id.progressBar) != null) {
            if (show) {
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        }*/
    }

    public void changeNavItemSelected(String nav) {
        BottomNavigationView bnv = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        if (bnv == null) {
            return;
        }

        Menu menu = bnv.getMenu();

        menu.findItem(R.id.navigation_buy).setIcon(
                new IconDrawable(getBaseContext(), FontAwesomeIcons.fa_home)
                        .colorRes(nav.equals("buy") ? R.color.colorPrimary : R.color.greyedText)
                        .sizeDp(14));

        menu.findItem(R.id.navigation_sell).setIcon(
                new IconDrawable(getBaseContext(), FontAwesomeIcons.fa_usd)
                        .colorRes(nav.equals("sell") ? R.color.colorPrimary : R.color.greyedText)
                        .sizeDp(14));

        menu.findItem(R.id.navigation_messages).setIcon(
                new IconDrawable(getBaseContext(), FontAwesomeIcons.fa_comment)
                        .colorRes(nav.equals("messages") ? R.color.colorPrimary : R.color.greyedText)
                        .sizeDp(14));

        menu.findItem(R.id.navigation_more).setIcon(
                new IconDrawable(getBaseContext(), FontAwesomeIcons.fa_bars)
                        .colorRes(nav.equals("more") ? R.color.colorPrimary : R.color.greyedText)
                        .sizeDp(14));
    }

    public void showDialog() {
        Log.v(TAG, "Showing Dialog");

        if (findViewById(R.id.filter_layout) == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FilterDialog newFragment = new FilterDialog();

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction.add(android.R.id.content, newFragment)
                    .addToBackStack(null).commit();
        }
    }
}
