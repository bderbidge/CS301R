package com.example.brandonderbidge.myapplication.login;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class LoginController {
    private static final String TAG = "LoginController";
    private LoginActivity loginActivity;



    boolean validData( String username, String password) {

        boolean isValid = true;
        if (username == null || username.equals("")) {
            Log.e(TAG, "username not provided");
            // make username red
            isValid = false;
        }
        if (password == null || password.equals("")) {
            Log.e(TAG, "password not provided");
            // make password red
            isValid = false;
        }

        return isValid;
    }


}
