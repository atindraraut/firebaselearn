package com.example.firebasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    private Button register;
    private Button login;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);
        auth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser !=null){
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }

    }

    public void registerclick(View view) {
        startActivity(new Intent(StartActivity.this,register.class));
        finish();
    }

    public void loginclick(View view) {
        startActivity(new Intent(StartActivity.this,login.class));
        finish();
    }
}