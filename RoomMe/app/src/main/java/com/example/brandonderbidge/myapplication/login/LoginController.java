package com.example.brandonderbidge.myapplication.login;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class LoginController {
    private static final String TAG = "LoginController";
    private LoginActivity loginActivity;


//
//    public void setLoginActivity(LoginActivity loginActivity) {
//        this.loginActivity = loginActivity;
//    }
//
//    public void login(String username, String password) {
//        Log.v(TAG,"Entering login");
//        if(!validData(false, username, password, null, null)) {
//            Log.e(TAG, "Invalid login Information");
//            this.loginActivity.createToast("Invalid Sign in information", Toast.LENGTH_SHORT);
//            loginActivity.showProgressWheel(true);
//            return;
//        }
//
//        //LoginTask task = new LoginTask();
//
//        //task.execute(new LoginCmdData(username, password, FirebaseInstanceId.getInstance().getToken()));
//
//        Log.v(TAG,"Exiting login");
//    }
//
//    void register(String username, String password, String firstname, String lastname) {
//        Log.v(TAG,"Entering register");
//        if(!validData(true, username, password, firstname, lastname)) {
//            Log.e(TAG, "Invalid register Information");
//            loginActivity.createToast("Invalid Register Information", Toast.LENGTH_SHORT);
//            loginActivity.showProgressWheel(true);
//            return;
//        }
//
//        //LoginTask task = new LoginTask();
//
//        //task.execute(new RegisterCmdData(username, password, firstname, lastname, FirebaseInstanceId.getInstance().getToken()));
//        Log.v(TAG,"Exiting register");
//    }

    boolean validData(/*boolean register,*/ String username, String password /*,String firstname, String lastname*/) {
        boolean isValid = true;
//        if (register) {
//            if (firstname == null || firstname.equals("")) {
//                Log.e(TAG, "firstname not provided");
//                // make firstname red
//                isValid = false;
//            }
//            if (lastname == null || lastname.equals("")) {
//                Log.e(TAG, "lastname not provided");
//                // make lastname red
//                isValid = false;
//            }
//        }

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

    /*private class LoginTask extends AsyncTask<LoginCmdData, Void, LoginResult> {
        @Override
        protected LoginResult doInBackground(LoginCmdData... params) {
            LoginResult loginResult;

            if (params[0].getClass().getSimpleName().equals("RegisterCmdData")) {
                RegisterCmdData registerCmdData = (RegisterCmdData) params[0];
                loginResult = ServerProxy.getInstance().register(registerCmdData.getUsername(), registerCmdData.getPassword(),
                        registerCmdData.getFirstName(), registerCmdData.getLastName(), FirebaseInstanceId.getInstance().getToken());
            } else {
                loginResult = ServerProxy.getInstance().login(params[0].getUsername(), params[0].getPassword(), FirebaseInstanceId.getInstance().getToken());
            }

            return loginResult;
        }

        @Override
        protected void onProgressUpdate(Void... result) {}

        @Override
        protected void onPostExecute(LoginResult result) {
            if (result.getMessage() == null) {
                onSuccess(result);
            } else {
                onError(result);
            }
        }
    }

    private void onSuccess(LoginResult result) {
        if (result.getAuthToken() == null) {
            Log.e(TAG, "AuthToken missing on successful return");
            loginActivity.createToast("There was an error with the server", Toast.LENGTH_SHORT);
        }
        if (result.getUsername() == null) {
            Log.e(TAG, "AuthToken missing on successful return");
            loginActivity.createToast("There was an error with the server", Toast.LENGTH_SHORT);
        }

        loginActivity.showProgressWheel(false);
        loginActivity.switchToLobbyActivity();
    }

    private void onError(LoginResult result) {
        Log.e(TAG, "login error:\n" + result.getMessage());
        loginActivity.showProgressWheel(false);
        loginActivity.createToast(result.getMessage(), Toast.LENGTH_LONG);
    }*/
}
