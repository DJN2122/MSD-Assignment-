package com.example.livediary.adapter;

// Import statements
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livediary.R;
import com.example.livediary.entities.Note;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes; // List to store notes for the adapter
    private List<Note> source; // Source list used for filtering during search
    private NoteListener noteListener; // Interface for handling note click events
    private Timer timer; // Timer for implementing delayed search

    // Constructor for NoteAdapter
    public NoteAdapter(List<Note> notes, NoteListener noteListener) {
        this.notes = notes;
        this.source = notes; // Initialize source with all notes
        this.noteListener = noteListener; // Initialize the note click listener
    }

    // Creates and returns a NoteViewHolder
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the note layout and create a new ViewHolder
        return new NoteViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_layout, parent, false));
    }

    // Binds the note data to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setNote(notes.get(position)); // Set note data in ViewHolder
        holder.noteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Notify the listener when a note is clicked
                noteListener.onNoteClicked(notes.get(position), position);
            }
        });
    }

    // Returns the total number of items in the adapter
    @Override
    public int getItemCount() {
        return notes.size();
    }

    // Returns the view type of the item at the given position (unused in this case)
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // ViewHolder class for the note items
    static class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitle, noteContent, timeAndDate;
        ConstraintLayout noteLayout;

        // Constructor to initialize ViewHolder elements
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find and assign UI elements from the layout
            noteTitle = itemView.findViewById(R.id.note_title_tv);
            noteContent = itemView.findViewById(R.id.note_content_tv);
            timeAndDate = itemView.findViewById(R.id.time_and_date_tv);
            noteLayout = itemView.findViewById(R.id.note_layout);
        }

        // Method to set note data in ViewHolder
        void setNote(Note note) {
            noteTitle.setText(note.getTitle());
            // Hide note content if it's empty, else set its text
            if (note.getContent().trim().isEmpty()) {
                noteContent.setVisibility(View.GONE);
            } else {
                noteContent.setText(note.getContent());
            }
            timeAndDate.setText(note.getDate()); // Set note's date and time

            // Set the background color for the note layout
            GradientDrawable drawable = (GradientDrawable) noteLayout.getBackground();
            if (note.getColor() != null && !note.getColor().isEmpty()) {
                drawable.setColor(Color.parseColor(note.getColor()));
            } else {
                drawable.setColor(Color.parseColor("#333333"));
            }
        }
    }

    // Method to perform search and filter notes
    public void search(String text) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Check if search text is empty
                if (text.trim().isEmpty()) {
                    notes = source; // Use all notes if search text is empty
                } else {
                    ArrayList<Note> list = new ArrayList<>();
                    // Filter notes based on the search text
                    for (Note item : source) {
                        if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                            list.add(item);
                        }
                    }
                    notes = list; // Update notes list with filtered results
                }
                // Update RecyclerView on the main thread
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void run() {
                        notifyDataSetChanged(); // Notify adapter of data change
                    }
                });
            }
        }, 300); // Delay the search execution by 300 milliseconds
    }

}