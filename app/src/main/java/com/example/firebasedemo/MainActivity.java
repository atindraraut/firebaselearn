package com.example.firebasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button register;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);

    }

    public void registerclick(View view) {
        startActivity(new Intent(MainActivity.this,register.class));
        finish();
    }

    public void loginclick(View view) {
        startActivity(new Intent(MainActivity.this,login.class));
        finish();
    }
}