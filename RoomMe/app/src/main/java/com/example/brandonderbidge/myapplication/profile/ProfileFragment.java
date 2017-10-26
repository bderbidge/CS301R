package com.example.brandonderbidge.myapplication.profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.brandonderbidge.myapplication.R;
import com.example.brandonderbidge.myapplication.buy.ContractActivity;
import com.example.brandonderbidge.myapplication.buy.FilterDialog;
import com.example.brandonderbidge.myapplication.login.LoginActivity;
import com.example.brandonderbidge.myapplication.model.Contract;
import com.example.brandonderbidge.myapplication.model.FilterModel;
import com.example.brandonderbidge.myapplication.model.Model;
import com.example.brandonderbidge.myapplication.sell.NewContractFragment;
import com.example.brandonderbidge.myapplication.sell.SellAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment" ;
    private Button signOut;
    private TextView fullName;
    private TextView email;
    private TextView phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        fullName = view.findViewById(R.id.fullName);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phoneNumber);

        fullName.setText( Model.instance().getCurrentUser().getFullName());
        email.setText(Model.instance().getCurrentUser().getEmail());
        phone.setText(Model.instance().getCurrentUser().getPhoneNumber());

        signOut = view.findViewById(R.id.signOut);
        getActivity().setTitle(R.string.profile);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(v.getContext(), LoginActivity.class);
                // set the new task and clear flags
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void onResume() {

        Log.e("DEBUG", "onResume of HomeFragment");
        super.onResume();
    }




    @Override
    public void onStart() {
        super.onStart();
    }
}
