package com.example.notesapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDBHelper {
    private static final String USERS_NODE = "users";

    private DatabaseReference usersRef;

    public FirebaseDBHelper() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference(USERS_NODE);
    }

    public void saveUser(String email, User user) {
        DatabaseReference userRef = usersRef.child(email);
        userRef.setValue(user);
    }
}
