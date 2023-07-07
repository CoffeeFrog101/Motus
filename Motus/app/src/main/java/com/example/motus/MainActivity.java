package com.example.motus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
private Button LoginBtn;
private Button SignBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginBtn = (Button) findViewById(R.id.button);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenLoginAct();
            }
        });

        SignBtn = (Button) findViewById(R.id.button2);
        SignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {OpenSignAct();}
        });
    }
    public void OpenLoginAct()
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    public void OpenSignAct()
    {
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }
}