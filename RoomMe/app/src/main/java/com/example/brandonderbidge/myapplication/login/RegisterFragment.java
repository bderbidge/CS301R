package com.example.brandonderbidge.myapplication.login;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.brandonderbidge.myapplication.R;
import com.example.brandonderbidge.myapplication.buy.BuyActivity;

import static android.content.ContentValues.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegisterFragment extends Fragment {

    private EditText usernameET;
    private EditText passwordET;
    private EditText firstnameET;
    private EditText lastnameET;
    private Button registerBtn;
    private LoginController loginController;
    private Button maleBtn;
    private Button femaleBtn;
    private ProgressBar mSpinner;

    public RegisterFragment() {}

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        setHasOptionsMenu(true);

        usernameET = (EditText) view.findViewById(R.id.username);
        passwordET = (EditText) view.findViewById(R.id.password);
        firstnameET = (EditText) view.findViewById(R.id.first_name);
        lastnameET = (EditText) view.findViewById(R.id.last_name);
        maleBtn = (Button) view.findViewById(R.id.male_btn);
        femaleBtn = (Button) view.findViewById(R.id.female_btn);

        registerBtn = (Button) view.findViewById(R.id.register_btn);
        //mSpinner = (ProgressBar) view.findViewById(R.id.progressBar);

        maleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maleBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.White));
                femaleBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.greyedText));

                maleBtn.setBackground(getResources().getDrawable(R.drawable.toggle_button_left_clicked, null));
                femaleBtn.setBackground(getResources().getDrawable(R.drawable.toggle_button_right, null));
            }
        });

        femaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                femaleBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.White));
                maleBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.greyedText));

                femaleBtn.setBackground(getResources().getDrawable(R.drawable.toggle_button_right_clicked, null));
                maleBtn.setBackground(getResources().getDrawable(R.drawable.toggle_button_left, null));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToBuyActivity();
            //    mSpinner.setVisibility(View.VISIBLE);
                /*loginController.register(usernameET.getText().toString(), passwordET.getText().toString(),
                        firstnameET.getText().toString(), lastnameET.getText().toString());*/
            }
        });

        return view;
    }

    public void switchToBuyActivity() {
        Log.d (TAG, "recycler clicked in app bar");
        Intent intent = new Intent(getActivity(),BuyActivity.class);
        startActivity(intent);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String firstname = "",
                lastname = "",
                username = "",
                password = "";

        Bundle args = getArguments();
        if (args != null) {
            firstname = args.getString(getString(R.string.EXTRA_FIRSTNAME));
            lastname = args.getString(getString(R.string.EXTRA_LASTNAME));
            username = args.getString(getString(R.string.EXTRA_USERNAME));
            password = args.getString(getString(R.string.EXTRA_PASSWORD));

            firstnameET.setText(firstname);
            lastnameET.setText(lastname);
            usernameET.setText(username);
            passwordET.setText(password);
        }

        loginController = new LoginController((LoginActivity) getActivity());

        loginController.validData(true, firstname, lastname, username, password);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home :
                getActivity().finish();
                return true;
            default :
                System.out.println("this should never happen!");
                return false;
        }
    }
}