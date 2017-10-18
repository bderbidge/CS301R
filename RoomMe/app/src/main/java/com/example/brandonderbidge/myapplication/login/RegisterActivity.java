package com.example.brandonderbidge.myapplication.login;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brandonderbidge.myapplication.R;
import com.example.brandonderbidge.myapplication.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.Executor;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "Register" ;
    private EditText usernameET;
    private EditText passwordET;
    private EditText firstnameET;
    private EditText lastnameET;
    private Button registerBtn;
    private LoginController loginController;
    private Button maleBtn;
    private Button femaleBtn;
    private ProgressBar mSpinner;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.v(TAG, "Starting onCreate of Register Activity");

        usernameET = (EditText) findViewById(R.id.username);
        passwordET = (EditText)findViewById(R.id.password);
        firstnameET = (EditText)findViewById(R.id.first_name);
        lastnameET = (EditText)findViewById(R.id.last_name);
        maleBtn = (Button) findViewById(R.id.male_btn);
        femaleBtn = (Button) findViewById(R.id.female_btn);
        mAuth = FirebaseAuth.getInstance();
        registerBtn = (Button) findViewById(R.id.register_btn);

        maleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maleBtn.setTextColor(Color.WHITE);
                femaleBtn.setTextColor(Color.GRAY);

                maleBtn.setBackground(getResources().getDrawable(R.drawable.toggle_button_left_clicked, null));
                femaleBtn.setBackground(getResources().getDrawable(R.drawable.toggle_button_right, null));
            }
        });

        femaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                femaleBtn.setTextColor(Color.WHITE);
                maleBtn.setTextColor(Color.GRAY);

                femaleBtn.setBackground(getResources().getDrawable(R.drawable.toggle_button_right_clicked, null));
                maleBtn.setBackground(getResources().getDrawable(R.drawable.toggle_button_left, null));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAccount(usernameET.getText().toString(), passwordET.getText().toString());
                //    mSpinner.setVisibility(View.VISIBLE);
                /*loginController.register(usernameET.getText().toString(), passwordET.getText().toString(),
                        firstnameET.getText().toString(), lastnameET.getText().toString());*/
            }
        });


    }

    public void createAccount(String email, String password){


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        int d = Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            // User is signed in

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(firstnameET.getText().toString() + " " + lastnameET.getText().toString())
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });
                        }
                        else {
                            // No user is signed in
                        }
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Unable to Register User",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            switchToMainActivity();
                        }

                        // ...
                    }
                });
    }
    public void switchToMainActivity() {
        Log.v(TAG, "Switching to MainActivity Activity");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
