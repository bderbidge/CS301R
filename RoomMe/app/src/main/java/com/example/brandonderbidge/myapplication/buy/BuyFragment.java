package com.example.brandonderbidge.myapplication.buy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.brandonderbidge.myapplication.R;
import com.example.brandonderbidge.myapplication.login.LoginActivity;
import com.example.brandonderbidge.myapplication.login.LoginController;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

/**
 * A placeholder fragment containing a simple view.
 */
public class BuyFragment extends Fragment {
    private static final String TAG = "BuyFragment";
    private BuyController buyController;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private BottomNavigationView bottomNavigationView;

    public BuyFragment() {}

    public void setLoginController(BuyController buyController) {
        this.buyController = buyController;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buy, container, false);
        setHasOptionsMenu(true);

        mAdapter = new MyAdapter();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottom_navigation);

        ((BuyActivity) getActivity()).changeNavItemSelected("buy");

//Attach the listener
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.navigation_buy:
                                ((BuyActivity) getActivity()).createToast("Buy Item Selected", Toast.LENGTH_LONG);
                                ((BuyActivity) getActivity()).changeNavItemSelected("buy");
                                break;
                            case R.id.navigation_sell:
                                ((BuyActivity) getActivity()).createToast("Sell Item Selected", Toast.LENGTH_LONG);
                                ((BuyActivity) getActivity()).changeNavItemSelected("sell");
                                break;
                            case R.id.navigation_messages:
                                ((BuyActivity) getActivity()).createToast("Messages Item Selected", Toast.LENGTH_LONG);
                                ((BuyActivity) getActivity()).changeNavItemSelected("messages");
                                break;
                            case R.id.navigation_more:
                                ((BuyActivity) getActivity()).createToast("More Item Selected", Toast.LENGTH_LONG);
                                ((BuyActivity) getActivity()).changeNavItemSelected("more");
                        }
                        return true;
                    }
                });



        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // specify an adapter (see also next example)

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
       super.onViewCreated(view, savedInstanceState);

        String username = "",
                password = "";

        Bundle args = getArguments();

        if (args != null) { //data was previously in fields, probably from a previous screen orientation
            /*username = args.getString(getString(R.string.EXTRA_USERNAME));
            password = args.getString(getString(R.string.EXTRA_PASSWORD));

            //set fields and check button based on what was passed in
            usernameText.setText(username);
            passwordText.setText(password);*/

        }

        buyController = new BuyController((BuyActivity) getActivity());

        buyController.validData(false, username, password, null, null);
    }
}
