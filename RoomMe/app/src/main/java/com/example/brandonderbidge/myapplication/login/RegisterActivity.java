package com.example.brandonderbidge.myapplication.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.brandonderbidge.myapplication.R;
import com.example.brandonderbidge.myapplication.main.MainActivity;
import com.example.brandonderbidge.myapplication.model.Model;
import com.example.brandonderbidge.myapplication.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "Register" ;
    private EditText emailET;
    private EditText passwordET;
    private EditText firstnameET;
    private EditText lastnameET;
    private EditText phonenumberET;
    private Button registerBtn;
    private LoginController loginController;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private    DatabaseReference myRef = database.getReference("Users");

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.v(TAG, "Starting onCreate of Register Activity");

        emailET = (EditText) findViewById(R.id.username);
        passwordET = (EditText)findViewById(R.id.password);
        firstnameET = (EditText)findViewById(R.id.first_name);
        lastnameET = (EditText)findViewById(R.id.last_name);
        phonenumberET = (EditText)findViewById(R.id.phone_number);
        mAuth = FirebaseAuth.getInstance();
        registerBtn = (Button) findViewById(R.id.register_btn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAccount(emailET.getText().toString(), passwordET.getText().toString());
                /*mSpinner.setVisibility(View.VISIBLE);
                loginController.register(emailET.getText().toString(), passwordET.getText().toString(),
                        firstnameET.getText().toString(), lastnameET.getText().toString());*/
            }
        });
    }

    public void createAccount(String email, String password){

        //make a pop up to add your phone number
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        int d = Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());


                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Unable to Register User",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            String ID = UUID.randomUUID().toString();
                            User user = new User(ID, firstnameET.getText().toString(),
                                    lastnameET.getText().toString(), phonenumberET.getText().toString(),
                                    emailET.getText().toString() );
                            Model.instance().setCurrentUser(user);
                            myRef.child(ID).setValue(user);
                            switchToMainActivity();
                        }
                    }
                });



    }
    public void switchToMainActivity() {
        Log.v(TAG, "Switching to MainActivity Activity");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
