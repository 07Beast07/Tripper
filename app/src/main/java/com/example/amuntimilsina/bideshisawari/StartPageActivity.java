package com.example.amuntimilsina.bideshisawari;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class StartPageActivity extends AppCompatActivity
//        implements GoogleApiClient.OnConnectionFailedListener
          {

    private Button signInGoogleBtn;
    private Button signInBtn;
    SharedPreferences sharedPreferences;
//    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 101;
//    private FirebaseAuth auth;
//    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onStart() {
        super.onStart();
//        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        signInBtn = findViewById(R.id.signInBtn);
        signInGoogleBtn = findViewById(R.id.sign_in_google);
        sharedPreferences = getSharedPreferences("user_info",0);
        if(!sharedPreferences.getString("phone","").equals("")){
            startActivity(new Intent(StartPageActivity.this,HomeActivity.class));
        }
//        auth = FirebaseAuth.getInstance();
//
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if(firebaseAuth.getCurrentUser() != null){
//                    startActivity(new Intent(StartPageActivity.this,HomeActivity.class));
//                }
//            }
//        };

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartPageActivity.this,LoginPageActivity.class));
            }
        });


//        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
//        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();

        signInGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
//                startActivityForResult(intent,REQ_CODE);
            }
        });
    }

//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Toast.makeText(StartPageActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQ_CODE){
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleResult(result);
//        }
//    }

//    private void handleResult(GoogleSignInResult result) {
//        if(result.isSuccess()){
//            GoogleSignInAccount account = result.getSignInAccount();
//            signUpTask(account);
//
//        }else{
//            Toast.makeText(StartPageActivity.this, "Auth went wrong!", Toast.LENGTH_SHORT).show();
//        }
//    }

//    private void signUpTask(GoogleSignInAccount account) {
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
//        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    FirebaseUser user = auth.getCurrentUser();
//
//                }else {
//                    Toast.makeText(StartPageActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }



}
