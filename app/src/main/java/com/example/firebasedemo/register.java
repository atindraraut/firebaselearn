package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.emailview);
        password = (EditText) findViewById(R.id.passwordview);
        Button registerbutton = (Button) findViewById(R.id.registerbutton);
        auth = FirebaseAuth.getInstance();
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
                    Toast.makeText(register.this,"registering user successfull",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(register.this,"registering user unsuccessfull",Toast.LENGTH_SHORT).show();

                }
            }


        });

    }
}