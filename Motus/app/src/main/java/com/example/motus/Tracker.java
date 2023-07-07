package com.example.motus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class Tracker extends AppCompatActivity {
    GoogleMap mMap;
    BottomNavigationView nav;
    public ImageView happyView, sadView, stressView, positveView;
    public int happy,sad,stressed,positive;
    private Dialog dialog;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        nav = findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.Track);
        mAuth = FirebaseAuth.getInstance();
        happy = 0;
        sad=0;
        stressed = 0;
        positive = 0;






        happyView=findViewById(R.id.imageView4);
        sadView =findViewById(R.id.imageView2);
        stressView =findViewById(R.id.imageView5);
        positveView =findViewById(R.id.imageView);

        dialog=new Dialog(this);


        FirebaseMessaging.getInstance().subscribeToTopic("Motus")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msgs = "Done";
                        if (!task.isSuccessful()) {
                            msgs = "Failed";
                        }

                    }
                });





        happyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecordHappy();
                ShowHappyDialog();
            }
        });
        sadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowSadDialog();
                RecordSad();
            }
        });
        stressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowStressDialog();
                RecordStressed();
            }
        });
        positveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowPositiveDialog();
                RecordPositive();

            }
        });


        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Track:
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
                        startActivity(new Intent(getApplicationContext(),AccountEdit.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
    }



    private void ShowHappyDialog() {
        dialog.setContentView(R.layout.happy_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button BtClose = dialog.findViewById(R.id.close2);
        BtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(Tracker.this,"Saved!!",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();

    }

    private void RecordHappy(){
         firebaseUser = mAuth.getInstance().getCurrentUser();
         String id = firebaseUser.getUid();
        Emotion_Track Emotion = new Emotion_Track(happy,sad,stressed,positive);
        DatabaseReference referencePro = FirebaseDatabase.getInstance("https://ultra-badge-347609-default-rtdb.europe-west1.firebasedatabase.app").getReference("register users").child(id);
        referencePro.child(firebaseUser.getUid()).setValue(Emotion).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            happy++;
            }
        });
    }

    private void ShowSadDialog(){
        dialog.setContentView(R.layout.sad_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button BtClose2 = dialog.findViewById(R.id.close4);
        BtClose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(Tracker.this,"Saved!!",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();}

    private void RecordSad() {

        firebaseUser = mAuth.getInstance().getCurrentUser();
        String id = firebaseUser.getUid();
        Emotion_Track Emotion = new Emotion_Track(happy,sad,stressed,positive);
        DatabaseReference referencePro = FirebaseDatabase.getInstance("https://ultra-badge-347609-default-rtdb.europe-west1.firebasedatabase.app").getReference("register users").child(id);
        referencePro.child(firebaseUser.getUid()).setValue(Emotion).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                sad++;
            }
        });
    }

    private void ShowStressDialog(){
        dialog.setContentView(R.layout.stressed_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button BtClose3 = dialog.findViewById(R.id.close3);
        BtClose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(Tracker.this,"Saved!!",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();}
    private void RecordStressed(){
        firebaseUser = mAuth.getInstance().getCurrentUser();
        String id = firebaseUser.getUid();
        Emotion_Track Emotion = new Emotion_Track(happy,sad,stressed,positive);
        DatabaseReference referencePro = FirebaseDatabase.getInstance("https://ultra-badge-347609-default-rtdb.europe-west1.firebasedatabase.app").getReference("register users").child(id);
        referencePro.child(firebaseUser.getUid()).setValue(Emotion).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                stressed++;
            }
        });

    }





    private void ShowPositiveDialog(){
        dialog.setContentView(R.layout.positive_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button BtClose4 = dialog.findViewById(R.id.close1);
        BtClose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(Tracker.this,"Saved!!",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();}

    private void RecordPositive(){
        firebaseUser = mAuth.getInstance().getCurrentUser();
        String id = firebaseUser.getUid();
        Emotion_Track Emotion = new Emotion_Track(happy,sad,stressed,positive);
        DatabaseReference referencePro = FirebaseDatabase.getInstance("https://ultra-badge-347609-default-rtdb.europe-west1.firebasedatabase.app").getReference("register users").child(id);
        referencePro.child(firebaseUser.getUid()).setValue(Emotion).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                positive++;
            }
        });


    }
}

