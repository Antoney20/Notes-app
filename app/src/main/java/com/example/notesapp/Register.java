package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    private EditText emailField, passwordField, cPasswordField, userName,phoneNumber,otpField;
    private Button  submitButton;
    private LinearLayout firstCard;

    private FirebaseAuth notesApp;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //firebase instance
        notesApp = FirebaseAuth.getInstance();

        //Button
  
        submitButton = findViewById(R.id.submitButton);
        //card
        firstCard = findViewById(R.id.firstCard);
  

        //progress bar.
        progressBar = findViewById(R.id.progressBar);


        //login Link
        TextView loginLink = findViewById(R.id.loginLink);
        // set on click listener.
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        //Handle the REGISTRATION PROCESS
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //perform registration process
                performRegistration();
            }

            private void performRegistration() {
                //get users data fed into forms
                emailField = findViewById(R.id.emailField);
                passwordField = findViewById(R.id.passwordField);
                cPasswordField = findViewById(R.id.cPasswordField);
                userName = findViewById(R.id.username);
                phoneNumber = findViewById(R.id.phoneNumber);
                //otpField = findViewById(R.id.otpField);


              String email = emailField.getText().toString();
              String password = passwordField.getText().toString();
              String username = userName.getText().toString();
              String phoneNum = phoneNumber.getText().toString();
              String cPassword = cPasswordField.getText().toString();

              // first validate data;
                boolean isValidated = validateData(email,password,username, phoneNum, cPassword);
                if (!isValidated){
                    return;
                }
                // authenticate user using firebase
                authenticateUser(email, password, username, phoneNum);

            }
              // authenticate user--- Firebase
            private void authenticateUser(String email, String password, String username, String phoneNum) {
                //progress bar change.
                progress(true);

                FirebaseAuth notesApp = FirebaseAuth.getInstance();

                notesApp.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progress(false);
                        if (task.isSuccessful()){
                   
                            Toast.makeText(Register.this, "Successfuly Registerd", Toast.LENGTH_SHORT).show();
                            // Proceed with login. Load the main activity.
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                    }

                        else{
                            Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            // progress bar.
            void progress(boolean inProgress){
                if (inProgress){
                    progressBar.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.GONE);
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    submitButton.setVisibility(View.VISIBLE);
                }
            }

            private boolean validateData(String email, String password, String username, String phoneNum,String cPassword){
                   // validate users input.
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailField.setError("Email is invalid");
                    return false;
                }
              if (password.length()<4){
                  passwordField.setError("Too short password");
                  return false;
              }
              if (username.length()<3){
                  userName.setError("Too short username");
                  return false;
              }

              if (!password.equals(cPassword)){
                  cPasswordField.setError("Password does not match");
              }
              if (phoneNum.length()<7){
                  phoneNumber.setError("Enter a valid phone number");
              }
              return true;
            }
        });


    }
}