package com.example.motus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
private Button Mainscrn;
    private FirebaseAuth mAuth;
    EditText editTextTextPersonName;
    EditText editTextTextPassword;
    Button LoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editTextTextPersonName =findViewById(R.id.editTextTextPersonName);
        editTextTextPassword =findViewById(R.id.editTextTextPassword);
        LoginBtn =findViewById(R.id.button3);



        Mainscrn = (Button) findViewById(R.id.button4);
        Mainscrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMainScrn();
            }
        });
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  email, password;
                mAuth = FirebaseAuth.getInstance();
                email = String.valueOf(editTextTextPersonName.getText());
                password = String.valueOf(editTextTextPassword.getText());


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this,"Enter email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this,"Enter password",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),"Welcome back!",Toast.LENGTH_SHORT).show();
                                    Intent intent= new Intent(getApplicationContext(),Tracker.class);
                                    startActivity(intent);
                                    finish();
                                    // Sign in success, update UI with the signed-in user's information

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });

    }
    public void OpenMainScrn()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}