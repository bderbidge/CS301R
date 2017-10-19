package com.example.brandonderbidge.myapplication.buy;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brandonderbidge.myapplication.model.Model;
import com.example.brandonderbidge.myapplication.R;

public class ContractActivity extends AppCompatActivity {
    TextView apartmentName ;
    TextView costOfRent ;
    TextView cityState ;
    TextView apartmentType;
    TextView sellerName;
    TextView sellBy;
    TextView notes;
    ImageView dial;
    ImageView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        apartmentName = (TextView)findViewById(R.id.apartmentName);
        costOfRent = (TextView)findViewById(R.id.costOfRent);
        cityState = (TextView)findViewById(R.id.cityState);
        apartmentType = (TextView)findViewById(R.id.genderRoomType);
        sellerName = (TextView)findViewById(R.id.sellerName);
        sellBy = (TextView)findViewById(R.id.sellBy);
        notes = (TextView)findViewById(R.id.notes);
        dial = (ImageView) findViewById(R.id.caller);
        email = (ImageView) findViewById(R.id.email);


        notes.setText(Model.instance().getSelectedContract().getAdditionalNotes());
        sellBy.setText(Model.instance().getSelectedContract().getSellBy());
        sellerName.setText(Model.instance().getSelectedContract().getSellerName());
        apartmentName.setText(Model.instance().getSelectedContract().getApartmentName());

        if(Model.instance().getSelectedContract().getMaritalStatus().equals("Married"))
            apartmentType.setText(Model.instance().getSelectedContract().getMaritalStatus());
        else
            apartmentType.setText(Model.instance().getSelectedContract().getMaritalStatus() + " " +
            Model.instance().getSelectedContract().getSex());

        String price = "$" + Model.instance().getSelectedContract().getPrice().toString();
        costOfRent.setText(price);
        cityState.setText(Model.instance().getSelectedContract().getCity() + " " + Model.instance().getSelectedContract().getState());



        dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialContactPhone(Model.instance().getSelectedContract().getPhoneNumber());
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email(Model.instance().getSelectedContract().getEmail());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    private void email(final String email) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Interested in Contract");
        startActivity(Intent.createChooser(intent, ""));
    }
}
