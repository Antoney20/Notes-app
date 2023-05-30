package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText EmailField, passwordField;
    private Button LoginButton;

    ProgressBar progressBar;
    public TextView registerLink;

    private FirebaseAuth notesApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //get firebase instance.
        notesApp = FirebaseAuth.getInstance();
        EmailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        LoginButton = findViewById(R.id.LoginButton);
        registerLink = findViewById(R.id.registerLink);
        //progress bar
        progressBar = findViewById(R.id.progressBar);

        //set on click listener to register  link.
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = EmailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();
                // first validate data;
                boolean isValidated = validateData(email,password);
                if (!isValidated){
                    return;
                }

                //Lets authenticate users. using firebase.
                authenticateUser(email,password);
            }

            private boolean validateData(String email, String password) {
                // validate users input.
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    EmailField.setError("Email is invalid");
                    return false;
                }
                if (password.length()<5){
                    passwordField.setError("Too short password");
                    return false;
                }
                return true;
            }

            private void authenticateUser(String email, String password) {
                FirebaseAuth notesApp = FirebaseAuth.getInstance();
                progress(true);
              notesApp.signInWithEmailAndPassword(email, password)
                      .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                          @Override

                public void onComplete(@NonNull Task< AuthResult > task) {
                              progress(false);
                              if (task.isSuccessful()) {
                                  // User is successfully logged in
                                  Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();

                                  // Redirect to the main activity

                                  Intent intent = new Intent(Login.this, MainActivity.class);
                                  startActivity(intent);
                                  finish();

                    } else {
                        // Login failed, display an error message
                        Toast.makeText(Login.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            }
            // progress bar.
            void progress(boolean inProgress){
                if (inProgress){
                    progressBar.setVisibility(View.VISIBLE);
                    LoginButton.setVisibility(View.GONE);
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    LoginButton.setVisibility(View.VISIBLE);
                }
            }

        });

    }
}