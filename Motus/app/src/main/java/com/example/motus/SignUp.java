package com.example.motus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private Button Mainscrn2;
    private FirebaseAuth mAuth;
    EditText editTextUsername;
    EditText editTextEmailAddress;
    EditText editTextPassword;
    Button SignBtn;
    private String Email,Name, Password;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextEmailAddress =findViewById(R.id.editTextEmailAddress);
        editTextPassword =findViewById(R.id.editTextPassword);
        editTextUsername =findViewById(R.id.editTextUsername);
        SignBtn =findViewById(R.id.button5);

        SignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  email, password,Username;
                mAuth = FirebaseAuth.getInstance();
                email = String.valueOf(editTextEmailAddress.getText());
                password = String.valueOf(editTextPassword.getText());
                Username= String.valueOf(editTextUsername.getText());


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignUp.this,"Enter email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUp.this,"Enter password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Username)) {
                    Toast.makeText(SignUp.this,"Enter Username",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();

                                    //UserProfileChangeRequest ProfileChange = new UserProfileChangeRequest.Builder().setDisplayName()


                                    Read_Write_Details CustomDits = new Read_Write_Details(Username);

                                    DatabaseReference referencePro = FirebaseDatabase.getInstance("https://ultra-badge-347609-default-rtdb.europe-west1.firebasedatabase.app").getReference("register users");
                                    referencePro.child(firebaseUser.getUid()).setValue(CustomDits).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                            firebaseUser.sendEmailVerification();
                                            Toast.makeText(SignUp.this, "Welcome to Motus.Check email",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent intent= new Intent(getApplicationContext(),Tracker.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();}
                                            else {
                                                // If sign in fails, display a message to the user.
                                                Toast.makeText(SignUp.this, "Please check if u input the credientials",
                                                        Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                    });



                                } else {

                                    Toast.makeText(SignUp.this, "connection to the DB has failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });




        Mainscrn2 = (Button) findViewById(R.id.button6);
        Mainscrn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMainScreen();
            }
        });


    }

    public void OpenMainScreen()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}