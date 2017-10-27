package com.example.brandonderbidge.myapplication.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.UUID;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private LoginController loginController;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Users");
    private Button login;
    private Button register;
    private TextView emailText;
    private TextView passwordText;
    private ProgressBar progressBar;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton signInButton;
    private TextView forgotPassword;
    private static int RC_SIGN_IN = 100;


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
        mAuth = FirebaseAuth.getInstance();
        forgotPassword = (TextView) findViewById(R.id.forgot_password);

                // Set the dimensions of the sign-in button.
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToForgotPasswordActivity();
            }
        });

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });
        this.loginController = new LoginController();


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    String email = user.getEmail();
                    Query allPostFromAuthor = myRef.orderByChild("email").equalTo(email);
                    allPostFromAuthor.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            if (snapshot.getValue() != null) {
                                for (DataSnapshot post : snapshot.getChildren()) {
                                    User user = post.getValue(User.class);

                                    Log.d(TAG, "MADE IT TO FIREBASE LOGIN");
                                    Model.instance().setCurrentUser(user);
                                    switchToMainActivity();

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


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
                    signIn(emailText.getText().toString(), passwordText.getText().toString());
                }
            }
       });

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
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

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {



            String email = currentUser.getEmail();
            Query allPostFromAuthor = myRef.orderByChild("email").equalTo(email);
            allPostFromAuthor.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    if (snapshot.getValue() != null) {
                        for (DataSnapshot post : snapshot.getChildren()) {
                            User user = post.getValue(User.class);

                            Log.d(TAG, "MADE IT TO FIREBASE LOGIN");
                            Model.instance().setCurrentUser(user);
                            switchToMainActivity();

                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }else {

                            FirebaseUser currentUser = mAuth.getCurrentUser();

                            if(currentUser != null) {

                                String email = currentUser.getEmail();
                                Query allPostFromAuthor = myRef.orderByChild("email").equalTo(email);
                                allPostFromAuthor.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {

                                        if (snapshot.getValue() != null) {
                                            for (DataSnapshot post : snapshot.getChildren()) {
                                                User user = post.getValue(User.class);

                                                Log.d(TAG, "MADE IT TO FIREBASE LOGIN");
                                                Model.instance().setCurrentUser(user);
                                                switchToMainActivity();

                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }

                        }

                        // ...
                    }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                final GoogleSignInAccount account = result.getSignInAccount();
                account.getEmail(); //From here make sure that it adds to the database.


                Query allPostFromAuthor = myRef.orderByChild("email").equalTo(account.getEmail());
                allPostFromAuthor.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        if(snapshot.getValue() != null) {
                            for (DataSnapshot post : snapshot.getChildren()) {
                                User user = post.getValue(User.class);

                                Log.d("THIS", "MADE IT TO FIREBASE LOGIN");
                                Model.instance().setCurrentUser(user);
                            }
                        }
                        else {

                            //create a new user based off of the google account
                            String ID = UUID.randomUUID().toString();
                            User user = new User(ID, account.getGivenName() +" " +
                                    account.getFamilyName(), "",
                                    account.getEmail());
                            Model.instance().setCurrentUser(user);
                            myRef.child(ID).setValue(user);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }



    public void switchToRegisterActivity(){

        Log.v(TAG, "Switching to RegisterActivity Activity");
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void switchToMainActivity() {
        Log.v(TAG, "Switching to MainActivity Activity");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void switchToForgotPasswordActivity(){
        Log.v(TAG, "Switching to ForgotPassword Activity");
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            mAuth.addAuthStateListener(mAuthListener);
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            String email = currentUser.getEmail();
                            Query allPostFromAuthor = myRef.orderByChild("email").equalTo(email);
                            allPostFromAuthor.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {

                                    if (snapshot.getValue() != null) {
                                        for (DataSnapshot post : snapshot.getChildren()) {
                                            User user = post.getValue(User.class);

                                            Log.d("THIS", "MADE IT TO FIREBASE LOGIN");
                                            Model.instance().setCurrentUser(user);


                                        }
                                    } else {

                                        //create a new user based off of the google account
                                        String ID = UUID.randomUUID().toString();
                                        User user = new User(ID, acct.getGivenName() + " " +
                                                acct.getFamilyName(), "",
                                                acct.getEmail());
                                        Model.instance().setCurrentUser(user);
                                        myRef.child(ID).setValue(user);
                                    }
                                    switchToMainActivity();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }


}
