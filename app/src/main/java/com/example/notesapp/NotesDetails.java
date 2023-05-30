package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class NotesDetails extends AppCompatActivity {

    private EditText noteTitle, notesDetails;
    private ImageButton saveNote;
    TextView pageTitleTv;
    String title, content;
    boolean isEditMode = false;
    TextView deleteText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);

        noteTitle = findViewById(R.id.title);
        notesDetails= findViewById(R.id.note_details);
        saveNote = findViewById(R.id.save_note);
        pageTitleTv = findViewById(R.id.page_title);
        deleteText = findViewById(R.id.delete);

        //Recieve data.
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");

        noteTitle.setText(title);
        notesDetails.setText(content);
        if (title != null && !title.isEmpty()){
            isEditMode = true;
        }

        if (isEditMode){
            pageTitleTv.setText("Edit your note. Now");
            deleteText.setVisibility(View.VISIBLE);
        }

        deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNoteFromFB();
            }


        });

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nTitle = noteTitle.getText().toString();
                String nDetails = notesDetails.getText().toString();

                if (nTitle == null || nTitle.isEmpty()){
                    noteTitle.setError("This field can't be Empty");
                    return;
                }
                if (nDetails == null || nDetails.isEmpty()){
                    noteTitle.setError("This field can't be Empty..");
                    return;
                }
                //add note
                Note note = new Note();
                note.setTitle(nTitle);
                note.setContent(nDetails);
                note.setTimestamp(Timestamp.now());

                saveNoteFb(note);
            }

        });

    }
    private void saveNoteFb(Note note) {
        DocumentReference documentReference;
        if (isEditMode) {
            documentReference = Utility.getCollectionRefferenceNotes().document(title);
        }else{
            documentReference = Utility.getCollectionRefferenceNotes().document();
        }
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(NotesDetails.this,"Successfuly added the note",Toast.LENGTH_SHORT);
                    finish();
                }
                else {
                    Toast.makeText(NotesDetails.this, "saving failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void deleteNoteFromFB() {
        DocumentReference documentReference;

            documentReference = Utility.getCollectionRefferenceNotes().document(title);

            documentReference = Utility.getCollectionRefferenceNotes().document();

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(NotesDetails.this,"Successfuly deleted the note",Toast.LENGTH_SHORT);
                    finish();
                }
                else {
                    Toast.makeText(NotesDetails.this, "delete failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}