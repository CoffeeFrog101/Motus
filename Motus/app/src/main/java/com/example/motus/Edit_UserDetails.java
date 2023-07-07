package com.example.motus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Edit_UserDetails extends AppCompatActivity {

    private String Name, Email;
    private FirebaseAuth mAuth;
    private Button cancel;
    private Button Save;
    private DatabaseReference Ref;
    private ImageView ChangePho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details);

        Save = findViewById(R.id.EditAcc);
        cancel = findViewById(R.id.cancel_button);


        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.pass);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        Ref = FirebaseDatabase.getInstance("https://ultra-badge-347609-default-rtdb.europe-west1.firebasedatabase.app").getReference("register users");

        //Profile pic
        ChangePho = findViewById(R.id.profilepic);
        Bundle undle = null;
        if (ChangePho != null) {
            undle = getIntent().getExtras();
        }
        if (undle != null) {
            Character pro = undle.getChar("pro");
            ChangePho.setImageResource(pro);
        }
        ChangePho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PicUpload.class));
                overridePendingTransition(0, 0);

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AccountEdit.class));
                overridePendingTransition(0, 0);
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Newem(email.getText().toString(), password.getText().toString());
                startActivity(new Intent(getApplicationContext(), AccountEdit.class));
                overridePendingTransition(0, 0);

            }
        });



    }
    EditText changed;

    private void Newem(String email, final String password) {

        changed = findViewById(R.id.changes);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication
        AuthCredential credential = EmailAuthProvider.getCredential(email, password); // Current Login Credentials


        assert user != null;
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Log.d("value", "User re-authenticated.");


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                user.updateEmail(changed.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Edit_UserDetails.this, "Email Changed" + " Current Email is " + changed.getText().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }


}