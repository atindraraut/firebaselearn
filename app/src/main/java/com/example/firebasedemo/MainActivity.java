package com.example.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
        ListView listView = findViewById(R.id.listview);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();



//        verifieduser();

        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myRef.setValue("hello,world");
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

        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter<String> aadapter = new ArrayAdapter<>(this, R.layout.list_view, list);
        listView.setAdapter(aadapter);

        DatabaseReference uservalue = myRef.child("users");
        uservalue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                list.clear();
                Log.d(TAG,"this is"+snapshot.toString());
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    users user = dataSnapshot.getValue(users.class);
                    Log.d(TAG,"secondfailure"+user.getUsername());
                    Log.d(TAG,"thirdfailure"+user.getEmail());
                    String txt = user.getUsername()+":"+user.getEmail();

                    Log.d(TAG, "onDataChange: "+txt);
                    list.add(txt);
                }
                Log.d(TAG,"list failure"+list);
//                aadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Log.d(TAG,"failed users");
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