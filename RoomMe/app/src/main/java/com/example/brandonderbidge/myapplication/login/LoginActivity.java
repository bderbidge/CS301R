package com.example.brandonderbidge.myapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.brandonderbidge.myapplication.buy.BuyActivity;
import com.example.brandonderbidge.myapplication.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "Starting onCreate of Login Activity");

        setContentView(R.layout.activity_login);

        this.loginController = new LoginController(this);

        if (savedInstanceState == null) {
            setLoginFragment(null);
        } else if (savedInstanceState.get(getString(R.string.TAG_currentfrag)).equals(getString(R.string.TAG_register))) {
            setRegisterFragment(savedInstanceState);
        } else {
            setLoginFragment(savedInstanceState);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        LoginFragment testLoginFrag = (LoginFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.TAG_login));
        RegisterFragment testRegFrag = (RegisterFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.TAG_register));

        outState.putString(getString(R.string.EXTRA_USERNAME), ((EditText) findViewById(R.id.user_name)).getText().toString());
        outState.putString(getString(R.string.EXTRA_PASSWORD), ((EditText) findViewById(R.id.password)).getText().toString());

        if(testRegFrag != null && testRegFrag.isVisible()) {
            outState.putString(getString(R.string.EXTRA_FIRSTNAME), ((EditText) findViewById(R.id.first_name)).getText().toString());
            outState.putString(getString(R.string.EXTRA_LASTNAME), ((EditText) findViewById(R.id.last_name)).getText().toString());

            outState.putString(getString(R.string.TAG_currentfrag), getString(R.string.TAG_register));
        } else if(testLoginFrag != null && testLoginFrag.isVisible()) {
            outState.putString(getString(R.string.TAG_currentfrag), getString(R.string.TAG_login));
        }
    }

    public void switchToBuyActivity() {
        Log.v(TAG, "Switching to Buy Activity");
        Intent buyIntent = new Intent(this, BuyActivity.class);
        startActivity(buyIntent);
    }

    public void setLoginFragment(Bundle savedInstanceState) {
        LoginFragment loginFrag = new LoginFragment();
        loginFrag.setArguments(savedInstanceState);

        loginFrag.setLoginController(loginController);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.login_fragment_container, loginFrag, getString(R.string.TAG_login))
                .commit();
    }

    public void setRegisterFragment(Bundle savedInstanceState) {
        RegisterFragment regFrag = new RegisterFragment();
        regFrag.setArguments(savedInstanceState);

        regFrag.setArguments(savedInstanceState);
        regFrag.setLoginController(loginController);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.login_fragment_container, regFrag, getString(R.string.TAG_register))
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
}
