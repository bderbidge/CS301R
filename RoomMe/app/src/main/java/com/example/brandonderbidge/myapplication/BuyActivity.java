package com.example.brandonderbidge.myapplication;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

public class BuyActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
//Attach the listener
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.recent_item:
                                Toast.makeText(BuyActivity.this, "Recent Item Selected", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.location_item:
                                Toast.makeText(BuyActivity.this, "Favorite Item Selected", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.favorite_item:
                                Toast.makeText(BuyActivity.this, "Location Item Selected", Toast.LENGTH_LONG).show();
                                break;
                        }
                        return true;
                    }
                });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }
}
