package com.example.motus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
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

public class Calender extends AppCompatActivity {
    BottomNavigationView nav;
   private CalendarView calender;
    EditText text;
    Button save;
    String Store;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        nav = findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.Calander);
        calender = findViewById(R.id.calendarView);
        save = findViewById(R.id.button11);
        text = findViewById(R.id.editTextTextPersonName2);






        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Track:
                        startActivity(new Intent(getApplicationContext(),Tracker.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Calander:
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


        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Store= i1+"/"+i2+"/"+i;
                Log.d("data",Store);



            }
        });


    }
//    private void calendarClicked(FirebaseUser firebaseUser){
//        String ID = firebaseUser.getUid();
//        DatabaseReference referencePro = FirebaseDatabase.getInstance("https://ultra-badge-347609-default-rtdb.europe-west1.firebasedatabase.app").getReference("register users").child(ID);
//        referencePro.child(Store).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.getValue() != null){
//                    text.setText(snapshot.getValue().toString());
//                }else {
//                    text.setText("No event saved");
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }

    public void saved(View view){
//        referencePro.child(Store).setValue(text.getText().toString());
        text.setText(Store);
    }
}