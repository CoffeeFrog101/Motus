package com.example.motus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class Stats extends AppCompatActivity {
    BottomNavigationView nav;
    private PieChart pie;
    private FirebaseAuth mAuth;
    public int happy,sad,stressed,positive;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        nav = findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.Stats);

        pie = findViewById(R.id.pieChart2);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ultra-badge-347609-default-rtdb.europe-west1.firebasedatabase.app");


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser == null)
        {
            Toast.makeText(Stats.this,"Your details arent available :(",Toast.LENGTH_SHORT).show();

        }else{
            showDits(firebaseUser);
        }

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

    private void showDits(FirebaseUser firebaseUser) {
        String ID = firebaseUser.getUid();
        DatabaseReference referencePro = FirebaseDatabase.getInstance("https://ultra-badge-347609-default-rtdb.europe-west1.firebasedatabase.app").getReference("register users").child(ID);
        referencePro.child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Emotion_Track EMO = snapshot.getValue(Emotion_Track.class);
                happy= EMO.happy;
                sad = EMO.sad;
                stressed=EMO.stressed;
                positive= EMO.positive;
                pie.addPieSlice(new PieModel("Happy",happy, Color.parseColor("#FFEA00")));
                pie.addPieSlice(new PieModel("Sad",sad, Color.parseColor("#89CFF0")));
                pie.addPieSlice(new PieModel("Stressed",stressed, Color.parseColor("#EE4B2B")));
                pie.addPieSlice(new PieModel("Positive",positive, Color.parseColor("#088F8F")));

                pie.startAnimation();
                pie.setClickable(false);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}