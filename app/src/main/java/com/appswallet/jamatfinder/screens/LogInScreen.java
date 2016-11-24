package com.appswallet.jamatfinder.screens;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import com.appswallet.jamatfinder.MainActivity;
import com.appswallet.jamatfinder.R;
import com.appswallet.jamatfinder.models.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LogInScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText inputEmail, inputPassword;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset, btnGoogeSignIn;

    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    private Uri UserImageuri;
    private String Username;
    String gEmail;

    String email ;
    String password;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFbIntances();   //Get Firebase auth instance
        setContentView(R.layout.activity_log_in_screen);
        initGoogleClient();
        initViews();
        viewsListners();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            auth.removeAuthStateListener(mAuthListener);
        }
    }


    private void getFbIntances() {

        auth = FirebaseAuth.getInstance();   //Get Firebase auth instance
        mDatabase = FirebaseDatabase.getInstance().getReference();
        authListner();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LogInScreen.this, MainActivity.class));
            finish();
        }
    }

    private void authListner() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                  //  sendGoogleUserObjectToFb(user);
                    Toast.makeText(LogInScreen.this,"onAuthStateChanged:signed_in:" + user.getUid(), Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    Toast.makeText(LogInScreen.this,"onAuthStateChanged:signed_out" , Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void initGoogleClient() {

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this /* FragmentActivity */,this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void initViews() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnGoogeSignIn = (Button)findViewById(R.id.googleSignIn) ;
    }

    private void viewsListners() {

        btnSignup.setOnClickListener(new SignUpListner());
        btnReset.setOnClickListener(new ResetPasswordListner());
        btnLogin.setOnClickListener(new SimpleLogInListner());
        btnGoogeSignIn.setOnClickListener(new GoogleSignInListner());
    }

    private void sendUserObjectToFb() {
        String refreshToken= FirebaseInstanceId.getInstance().getToken();
        userId =  auth.getCurrentUser().getUid();

        User userFb = new User(email,password,refreshToken);
        mDatabase.child("user").child("userId: "+userId).setValue(userFb);
//        mDatabase.child("user").push().child(userId).setValue(userFb);
    }

    private void sendGoogleUserObjectToFb(GoogleSignInAccount account) {

        String refreshToken= FirebaseInstanceId.getInstance().getToken();
        User userFb = new User(account.getEmail(),refreshToken);
        mDatabase.child("user").child("userId: "+account.getId()).setValue(userFb);
        Toast.makeText(LogInScreen.this, "User credentials are sent to firebase server", Toast.LENGTH_SHORT).show();
//        mDatabase.child("user").child("userId: "+userId).setValue(userFb);
    }

//    private void sendGoogleUserObjectToFb(String googleEmail, String googleUid) {
//
//        String refreshToken= FirebaseInstanceId.getInstance().getToken();
//        User userFb = new User(googleEmail,refreshToken);
//        mDatabase.child("user").child("userId: "+googleUid).setValue(userFb);
//        Toast.makeText(LogInScreen.this, "User credentials are sent to firebase server", Toast.LENGTH_SHORT).show();
////        mDatabase.child("user").child("userId: "+userId).setValue(userFb);
//    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,"GoogleSignInConnection Failed",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResults(result);
    }


    private void handleSignInResults(GoogleSignInResult result) {
        if(result.isSuccess()){
            Toast.makeText(this,"SignIn successfully",Toast.LENGTH_LONG).show();
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);
            sendGoogleUserObjectToFb(account);
            Intent homeIntent = new Intent(LogInScreen.this, MainActivity.class);
            startActivity(homeIntent);
            finish();
        }
    }


    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {

        Toast.makeText(LogInScreen.this, "firebaseAuthWithGoogle:"+ account.getId(), Toast.LENGTH_SHORT).show();

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //sendGoogleUserObjectToFb( task.getResult().getUser().getEmail(),task.getResult().getUser().getUid());
                        Toast.makeText(LogInScreen.this, "signInWithCredential:onComplete::" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        if (!task.isSuccessful()) {
                            Toast.makeText(LogInScreen.this, "signInWithCredential:" + task.getException(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(LogInScreen.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private class ResetPasswordListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(LogInScreen.this, ResetPasswordScreen.class));
        }
    }

    private class SignUpListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(LogInScreen.this, SignupScreen.class));
        }
    }

    private class GoogleSignInListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    private class SimpleLogInListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {


            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            //authenticate user
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LogInScreen.this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            progressBar.setVisibility(View.GONE);
                            if (!task.isSuccessful()) {
                                // there was an error
                                if (password.length() < 6) {
                                    inputPassword.setError(getString(R.string.minimum_password));
                                } else {
                                    Toast.makeText(LogInScreen.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Intent intent = new Intent(LogInScreen.this, MainActivity.class);
                                sendUserObjectToFb();
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }
    }
}
