package com.example.motus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AccountEdit extends AppCompatActivity {
    BottomNavigationView nav;
    private TextView Welcome , Email , Password,Usernamee ;
    private ProgressBar Bar;
    private String email,password,Username;
    private ImageView iconPro;
    private FirebaseAuth mAuth;
    private Button Edit_acc;
    private Button Log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit);

        Welcome = findViewById(R.id.Welcome);
        Email = findViewById(R.id.Email);
        Usernamee = findViewById(R.id.Username);
        Bar = findViewById(R.id.progressBar2);
        Edit_acc = findViewById(R.id.EditAcc);
        Log= findViewById(R.id.logout);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ultra-badge-347609-default-rtdb.europe-west1.firebasedatabase.app");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser == null)
        {
            Toast.makeText(AccountEdit.this,"Your details arent available :(",Toast.LENGTH_SHORT).show();

        }else{
            Bar.setVisibility(View.VISIBLE);
            showsUser(firebaseUser);
        }
        //logout
        Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                overridePendingTransition(0,0);
            }
        });

        //Edit Account
        Edit_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Edit_UserDetails.class));
                overridePendingTransition(0,0);
            }
        });

        //Profile pic
        iconPro = findViewById(R.id.profilepic);
        iconPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),PicUpload.class));
                overridePendingTransition(0,0);

            }
        });





//        navigation
        nav = findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.Stats);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Track:
                        startActivity(new Intent(getApplicationContext(),Tracker.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Calander:
                        startActivity(new Intent(getApplicationContext(),Calender.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Stats:
                        startActivity(new Intent(getApplicationContext(),Stats.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Profile:
                        return true;

                }
                return false;
            }
        });
    }
    private void showsUser( FirebaseUser firebaseUser)
    {
        String ID = firebaseUser.getUid();

        DatabaseReference referencePro = FirebaseDatabase.getInstance("https://ultra-badge-347609-default-rtdb.europe-west1.firebasedatabase.app").getReference("register users");
        referencePro.child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Read_Write_Details Custom = snapshot.getValue(Read_Write_Details.class);

                Username= Custom.Username;
                email = firebaseUser.getEmail();
                Email.setText(email);
                Welcome.setText("Welcome, "+Username);
                Usernamee.setText(Username);
                Bar.setVisibility(View.GONE);}


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountEdit.this,"something went wrong :(",Toast.LENGTH_SHORT).show();

            }
        });

    }
}