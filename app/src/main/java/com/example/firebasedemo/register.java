package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseAuth auth;
    private TextView signinclick;
    public static final String TAG = "tagg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.emailview);
        password = findViewById(R.id.passwordview);
        auth = FirebaseAuth.getInstance();
        signinclick = findViewById(R.id.signinclick);

        signinclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this,login.class));
                finish();
            }
        });
    }

    public void registerbuttonclicked(View view) {
        String txt_email = email.getText().toString();
        String txt_password = password.getText().toString();

        if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
            Toast.makeText(register.this,"empty credentials",Toast.LENGTH_SHORT).show();
        }else if(txt_password.length() < 6){
            Toast.makeText(register.this,"password too short",Toast.LENGTH_SHORT).show();
        }else{
            resisteruser(txt_email,txt_password);
        }
    }

    private void resisteruser(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser user = auth.getCurrentUser();
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                          Toast.makeText(register.this,"email verification link sent",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: email not sent"+e.getMessage());
                        }
                    });
                    Toast.makeText(register.this,"registering user successfull",Toast.LENGTH_SHORT).show();
                    auth.signOut();
                    startActivity(new Intent(register.this, login.class));
                    finish();
                }else{
                    Toast.makeText(register.this,"registering user unsuccessfull",Toast.LENGTH_SHORT).show();

                }
            }


        });

    }
}