package com.example.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button logout,verifybutton;
    private FirebaseAuth auth;
    private TextView verified;
    public static final String TAG = "tagg";
    SwipeRefreshLayout refreshlayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        auth = FirebaseAuth.getInstance();
        verified = findViewById(R.id.verified);
        verifybutton = findViewById(R.id.verifybutton);
        refreshlayout =findViewById(R.id.swipetorefresh);



        verifieduser();

        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                verifieduser();
                refreshlayout.setRefreshing(false);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Toast.makeText(MainActivity.this, "loged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,login.class));
                finish();
            }
        });


    }



    private void verifieduser() {
        FirebaseUser user = auth.getCurrentUser();

        if (!user.isEmailVerified()){
            verified.setVisibility(View.VISIBLE);
            verifybutton.setVisibility(View.VISIBLE);

            verifybutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MainActivity.this,"email verification link sent",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: email not sent"+e.getMessage());
                        }
                    });
                }
            });
        }
    }

}