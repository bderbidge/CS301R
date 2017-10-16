package com.example.brandonderbidge.myapplication.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brandonderbidge.myapplication.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private EditText usernameText;
    private EditText passwordText;
    private Button loginBtn;
    private TextView registerFragmentBtn;
    private LoginController loginController;
    private ProgressBar mSpinner;

    public LoginFragment() {}

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        setHasOptionsMenu(true);

        usernameText = (EditText) view.findViewById(R.id.username);
        passwordText = (EditText) view.findViewById(R.id.password);
        loginBtn = (Button) view.findViewById(R.id.login_btn);
        registerFragmentBtn = (TextView) view.findViewById(R.id.register_fragment_button);
        //mSpinner = (ProgressBar) view.findViewById(R.id.progressBar);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mSpinner.setVisibility(View.VISIBLE);
                //loginController.login(usernameText.getText().toString(), passwordText.getText().toString());
                if (loginController.validData(false, usernameText.getText().toString(), passwordText.getText().toString(), null, null)) {
                    ((LoginActivity) getActivity()).createToast("Welcome, " + usernameText.getText().toString(), Toast.LENGTH_SHORT);
                    ((LoginActivity) getActivity()).switchToMainActivity();
                } else {
                    ((LoginActivity) getActivity()).createToast("Invalid username and/or password", Toast.LENGTH_SHORT);
                }
            }
        });

        registerFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment = new RegisterFragment();
                registerFragment.setLoginController(loginController);

                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.login_fragment_container, registerFragment)
                        .addToBackStack("register").commit();
            }
        });

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

        loginController = new LoginController((LoginActivity) getActivity());

        loginController.validData(false, username, password, null, null);
    }
}
