package com.example.brandonderbidge.myapplication.buy;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.brandonderbidge.myapplication.model.Model;
import com.example.brandonderbidge.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    ImageView heart;
    ImageView textMessage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myUser = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Contract");
        apartmentName = (TextView)findViewById(R.id.apartmentName);
        costOfRent = (TextView)findViewById(R.id.costOfRent);
        cityState = (TextView)findViewById(R.id.cityState);
        apartmentType = (TextView)findViewById(R.id.genderRoomType);
        sellerName = (TextView)findViewById(R.id.sellerName);
        sellBy = (TextView)findViewById(R.id.sellBy);
        notes = (TextView)findViewById(R.id.notes);
        dial = (ImageView) findViewById(R.id.caller);
        email = (ImageView) findViewById(R.id.email);
        heart = (ImageView) findViewById(R.id.favoriteHeart);
        textMessage = (ImageView) findViewById(R.id.text_img);

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

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Model.instance().getCurrentUser().getFavoriteContracts().add(Model.instance().getSelectedContract());
                myUser.child(Model.instance().getCurrentUser().getID()).setValue(Model.instance().getCurrentUser());
            }
        });

        textMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSeller(Model.instance().getSelectedContract().getPhoneNumber());
            }
        });

        boolean success = false;
        if(Model.instance().getCurrentUser().getFavoriteContracts() != null) {
            for (int i = 0; i < Model.instance().getCurrentUser().getFavoriteContracts().size(); i++) {
                if (Model.instance().getCurrentUser().getFavoriteContracts().get(i).getID().equals(Model
                        .instance().getSelectedContract().getID()))
                    success = true;
            }
        }

        if(success)
            heart.setImageResource(R.drawable.ic_full_heart);
        else
            heart.setImageResource(R.drawable.ic_action_favorite);

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean success = false;
                for(int i = 0; i < Model.instance().getCurrentUser().getFavoriteContracts().size(); i++)
                {
                    if(Model.instance().getCurrentUser().getFavoriteContracts().get(i).getID()
                            .equals(Model.instance().getSelectedContract().getID()))
                        success = true;
                }

                if(success) {
                    heart.setImageResource(R.drawable.ic_action_favorite);


                    for(int i = 0; i < Model.instance().getCurrentUser().getFavoriteContracts().size(); i++)
                    {
                        if(Model.instance().getCurrentUser().getFavoriteContracts().get(i).getID()
                                .equals(Model.instance().getSelectedContract().getID()))
                            Model.instance().getCurrentUser().getFavoriteContracts().remove(i);
                    }

                    myUser.child(Model.instance().getCurrentUser().getID()).setValue(Model
                            .instance().getCurrentUser());
                }
                else {
                    heart.setImageResource(R.drawable.ic_full_heart);
                    Model.instance().getCurrentUser().getFavoriteContracts().add(Model
                            .instance().getSelectedContract());
                    myUser.child(Model.instance().getCurrentUser().getID()).setValue(Model
                            .instance().getCurrentUser());

                }
            }
        });


    }


    public void textSeller(String phoneNumber){

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("smsto:" + phoneNumber));
        startActivity(sendIntent);
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
