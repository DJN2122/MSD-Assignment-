package com.example.livediary.activities;

// Import necessary Android and Java classes
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.livediary.R;
import com.example.livediary.adapter.NoteAdapter;
import com.example.livediary.adapter.NoteListener;
import com.example.livediary.database.NoteDatabase;
import com.example.livediary.entities.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteListener {

    private static final String TAG = "MainActivity";

    // Initialize ActivityResultLauncher, MediaPlayer, Views, RecyclerView and related variables
    private ActivityResultLauncher<Intent> launcher;
    MediaPlayer player;
    ImageView addNote;
    EditText search;
    RecyclerView notesRecyclerView;
    List<Note> noteList;
    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        addNote = findViewById(R.id.add_note);
        search = findViewById(R.id.search);

        // Initialize RecyclerView and set its layout manager
        notesRecyclerView = findViewById(R.id.recycler_view);
        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );

        // Initialize noteList and set up RecyclerView adapter
        noteList = new ArrayList<>();
        adapter = new NoteAdapter(noteList, this);
        notesRecyclerView.setAdapter(adapter);

        // Set up a text changed listener for search functionality
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Before text changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // On text changed
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // After text changed, filter notes based on search text
                if (noteList.size() != 0) {
                    adapter.search(editable.toString());
                }
            }
        });

        // Set up onClick listener for the add note button
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch CreateNote activity for result
                launcher.launch(new Intent(getApplicationContext(), CreateNote.class));
            }
        });

        // Initialize launcher for activity results
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // If the result is OK, refresh notes
                        getNotes();
                    } else {
                        // Log error if result is not OK
                        Log.e(TAG, "Error: " + result.getResultCode());
                    }
                });

        // Load notes from database
        getNotes();
    }

    private void getNotes() {
        // AsyncTask to retrieve notes from the database
        @SuppressLint("StaticFieldLeak")
        class GetNotesTask extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                // Perform database operation to get all notes
                return NoteDatabase.getInstance(getApplicationContext())
                        .dao().getAllNotes();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                // Update noteList with retrieved notes and notify adapter
                noteList.clear();
                noteList.addAll(notes);
                adapter.notifyDataSetChanged();

                // Scroll RecyclerView to top
                notesRecyclerView.smoothScrollToPosition(0);
                notesRecyclerView.smoothScrollToPosition(0);
            }
        }

        // Execute AsyncTask to get notes
        new GetNotesTask().execute();
    }

    @Override
    public void onNoteClicked(Note note, int position) {
        // Handle note click: launch CreateNote activity for viewing/editing a note
        Intent intent = new Intent(getApplicationContext(), CreateNote.class);
        intent.putExtra("isView", true);
        intent.putExtra("note", note);
        launcher.launch(intent);
    }
}
