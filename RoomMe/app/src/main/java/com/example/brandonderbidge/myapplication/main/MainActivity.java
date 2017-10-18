package com.example.brandonderbidge.myapplication.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.brandonderbidge.myapplication.R;
import com.google.firebase.storage.StorageReference;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by justinbrunner on 10/15/17.
 */

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Starting onCreate of Login Activity");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Iconify.with(new FontAwesomeModule());

        this.mainController = new MainController(this);

        setSellFragment(savedInstanceState);
    }

    public void createToast(String message, int toastLength) {
        Toast.makeText(getBaseContext(), message, toastLength).show();
    }

    public void setSellFragment(Bundle savedInstanceState) {
        MainFragment mainFrag = new MainFragment();
        mainFrag.setArguments(savedInstanceState);

        mainFrag.setMainController(mainController);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.activity_main_fragment_container, mainFrag, getString(R.string.TAG_main_frag))
                .commit();
    }
}
