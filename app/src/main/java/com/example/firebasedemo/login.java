package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    private static int RC_SIGN_IN =100;
    private EditText email;
    private EditText password;
    private FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;
    private ImageButton sign_in_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser =auth.getCurrentUser();
        TextView forgotpassword = findViewById(R.id.forgotpassword);
        TextView signupclick = findViewById(R.id.signupclick);
//        sign_in_button = findViewById(R.id.sign_in_button);
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        // Build a GoogleSignInClient with the options specified by gso.
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        // Check for existing Google Sign In account, if the user is already signed in
//        // the GoogleSignInAccount will be non-null.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (firebaseUser !=null){
            startActivity(new Intent(login.this, MainActivity.class));
            finish();
        }

//        sign_in_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent signinintent = mGoogleSignInClient.getSignInIntent();
//                startActivityForResult(signinintent,RC_SIGN_IN);
//            }
//        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_email = email.getText().toString();
                String text_password = password.getText().toString();
                //loginuser(text_email,text_password);
                if(TextUtils.isEmpty(text_email) || TextUtils.isEmpty(text_password)){
                    Toast.makeText(login.this,"empty credentials",Toast.LENGTH_SHORT).show();
                }else if(text_password.length() < 6){
                    Toast.makeText(login.this,"password too short",Toast.LENGTH_SHORT).show();
                }else{
                    loginuser(text_email,text_password);
                }
            }
        });
        signupclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,register.class));
                finish();
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(login.this,"clicked",Toast.LENGTH_SHORT).show();

                final EditText resetmail = new EditText(v.getContext());
                final AlertDialog.Builder passwordresetdialog = new AlertDialog.Builder(v.getContext());
                passwordresetdialog.setTitle("Reset password?");
                passwordresetdialog.setMessage("Enter your Email to receive reset link.");
                passwordresetdialog.setView(resetmail);

                passwordresetdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetmail.getText().toString();
                        auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(login.this,"reset link sent",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(login.this,"email link not sent",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordresetdialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordresetdialog.show();
            }
        });
    }

    private void loginuser(String text_email, String text_password) {
        auth.signInWithEmailAndPassword(text_email,text_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(login.this,"login successfull",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(login.this,MainActivity.class));
                finish();
            }
        });
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//        }
//    }
//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//            startActivity(new Intent(login.this,MainActivity.class));
//            // Signed in successfully, show authenticated UI.
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.d("message",e.toString());
//        }
//    }
}