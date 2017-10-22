package com.example.brandonderbidge.myapplication.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brandonderbidge.myapplication.main.MainActivity;
import com.example.brandonderbidge.myapplication.R;
import com.example.brandonderbidge.myapplication.model.Contract;
import com.example.brandonderbidge.myapplication.model.Model;
import com.example.brandonderbidge.myapplication.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private LoginController loginController;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    private Button login;
    private Button register;
    private TextView emailText;
    private TextView passwordText;
    private ProgressBar progressBar;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "Starting onCreate of Login Activity");

        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login_btn);
        register = (Button) findViewById(R.id.register);
        emailText = (TextView) findViewById(R.id.email);
        passwordText = (TextView) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        this.loginController = new LoginController();


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());


                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(loginController.validData(emailText.getText().toString(), passwordText.getText().toString() )) {
                    progressBar.setVisibility(View.VISIBLE);
                    signIn(emailText.getText().toString(), passwordText.getText().toString());
                }
            }
       });

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToRegisterActivity();
            }

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }



    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            switchToMainActivity();
                        }

                        // ...
                    }
                });


        Query allPostFromAuthor = myRef.orderByChild("email").equalTo(email);

// Add listener for Firebase response on said query
        allPostFromAuthor.addValueEventListener( new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot post : dataSnapshot.getChildren() ){
                    User user = post.getValue(User.class);
                    Model.instance().setCurrentUser(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void switchToRegisterActivity(){

        Log.v(TAG, "Switching to MainActivity Activity");
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void switchToMainActivity() {
        Log.v(TAG, "Switching to MainActivity Activity");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
