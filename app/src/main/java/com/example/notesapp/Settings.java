package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Settings extends AppCompatActivity {
    private TextView textViewinfo, welcomeUser;
    private ImageButton homeBtn, logoutBtn ;
    private LinearLayout accountLay, supportLay, privacyLay, logoutLay;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        textViewinfo = findViewById(R.id.profile_info);
        welcomeUser = findViewById(R.id.welcomeusertxt);
        homeBtn = findViewById(R.id.home_btn);
        logoutBtn = findViewById(R.id.logout_btn);
        accountLay = findViewById(R.id.account_lay);
        supportLay = findViewById(R.id.support_lay);
        privacyLay = findViewById(R.id.privacy_lay);
        logoutLay = findViewById(R.id.logout_lay);
          //check user
        if (currentUser != null) {


            String email = currentUser.getEmail();
            String welcomeMsg = "Welcome,"+ email;
            welcomeUser.setText(welcomeMsg);

        } else {

        }

        //home navigation.
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this,MainActivity.class ));
                finish();
            }
        });
        //handle logout.
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                Toast.makeText(Settings.this,"Success",Toast.LENGTH_SHORT);
                startActivity(new Intent(Settings.this, Login.class));
                finish();
            }
        });



    }
}