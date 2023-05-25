package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder> {
    //TextView noteTitle, noteContent, noteTimestamp;
    Context context;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
        holder.noteTitle.setText(note.title);
        holder.noteContent.setText(note.content);
        holder.noteTimestamp.setText(Utility.timestampToString(note.timestamp));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NotesDetails.class);
                intent.putExtra("title",note.title);
                intent.putExtra("content",note.content);
               // String docId = this.getSnapshots().getSnapshot(position);
               // intent.putExtra("time",note.timestamp);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_note_item,parent, false);
         return new NoteViewHolder(view);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView noteTitle, noteContent, noteTimestamp;

    public NoteViewHolder(@NonNull View itemView){
        super(itemView);
        noteTitle = itemView.findViewById(R.id.note_title);
        noteContent = itemView.findViewById(R.id.note_content);
        noteTimestamp = itemView.findViewById(R.id.note_timestamp);


    }
}
}

