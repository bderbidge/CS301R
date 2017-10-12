package com.example.brandonderbidge.myapplication.buy;


import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.brandonderbidge.myapplication.R;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class BuyActivity extends AppCompatActivity {
    private String TAG = "BuyActivity";
    private BuyController buyController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        Iconify.with(new FontAwesomeModule());

        this.buyController = new BuyController(this);

        BuyFragment buyFragment = new BuyFragment();
        buyFragment.setArguments(savedInstanceState);

        buyFragment.setArguments(savedInstanceState);
        buyFragment.setBuyController(buyController);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.buy_fragment_container, buyFragment, getString(R.string.TAG_buy))
                .addToBackStack("register")
                .commit();
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
                        .sizeDp(24));

        menu.findItem(R.id.navigation_sell).setIcon(
                new IconDrawable(getBaseContext(), FontAwesomeIcons.fa_usd)
                        .colorRes(nav.equals("sell") ? R.color.colorPrimary : R.color.greyedText)
                        .sizeDp(24));

        menu.findItem(R.id.navigation_messages).setIcon(
                new IconDrawable(getBaseContext(), FontAwesomeIcons.fa_comment)
                        .colorRes(nav.equals("messages") ? R.color.colorPrimary : R.color.greyedText)
                        .sizeDp(24));

        menu.findItem(R.id.navigation_more).setIcon(
                new IconDrawable(getBaseContext(), FontAwesomeIcons.fa_bars)
                        .colorRes(nav.equals("more") ? R.color.colorPrimary : R.color.greyedText)
                        .sizeDp(24));
    }
}
