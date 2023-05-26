package com.example.notesapp;

import static com.google.firebase.firestore.FirebaseFirestore.*;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;

import java.text.SimpleDateFormat;

public class Utility {
    static CollectionReference getCollectionRefferenceNotes(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return getInstance().collection("notes")
                .document(currentUser.getUid()).collection("my_notes");

    }
    static String timestampToString(Timestamp timestamp){
        return new SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate());
    }
}
