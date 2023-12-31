package com.example.livediary.activities;

// Import statements
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.livediary.R;
import com.example.livediary.database.NoteDatabase;
import com.example.livediary.entities.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNote extends AppCompatActivity {
    // UI elements for the note
    EditText noteTitle, noteContent;
    ImageView backButton, doneButton;
    TextView timeAndDate;
    String color;
    Note alreadyNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        initializeVariables(); // Initialize UI elements and variables
        // Check if the activity is opened for viewing or updating an existing note
        if (getIntent().getBooleanExtra("isView",false)){
            alreadyNote = (Note) getIntent().getSerializableExtra("note");
            viewOrUpdateNote();
        }
        // Listener for the back button to navigate back
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        // Display the current date and time
        timeAndDate.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())
        );
        // Listener for the done button to save the note
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote(); // Trigger note saving process
            }
        });
        initializeBottomSheet(); // Initialize color selection bottom sheet

    }
    // Method to populate UI with note details for editing
    private void viewOrUpdateNote() {
        noteTitle.setText(alreadyNote.getTitle());
        noteContent.setText(alreadyNote.getContent());
    }
    // Initialize UI elements by finding them in the layout
    private void initializeVariables() {
        color = "";
        backButton = findViewById(R.id.back_button);
        doneButton = findViewById(R.id.done_button);
        noteTitle = findViewById(R.id.note_title);
        noteContent = findViewById(R.id.note_content);
        timeAndDate = findViewById(R.id.time_and_date);
    }

    // Method to save the note
    private void saveNote() {
        // TODO 1: Check if the note title is not empty
        if (noteTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Note title can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO 2: Check if the note content is not empty
        else if (noteTitle.getText().toString().trim().isEmpty() &&
                noteContent.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Note can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO 3: Create a new Note object and set its properties
        final Note note = new Note();
        note.setTitle(noteTitle.getText().toString());
        note.setContent(noteContent.getText().toString());
        note.setDate(timeAndDate.getText().toString());
        note.setColor(color);

        // TODO 4: If it's an update operation, set the note ID
        if (alreadyNote != null) {
            note.setId(alreadyNote.getId());
        }

        // TODO 5: Save the note in the database using an AsyncTask
        class SaveNoteTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                // here we insert the note object to database
                NoteDatabase.getInstance(getApplicationContext()).dao().insert(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                // TODO 6: Set the result as RESULT_OK and finish the activity
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        // TODO 7: Execute the AsyncTask to save the note
        new SaveNoteTask().execute();
    }
    // Initialize the color selection bottom sheet
    private void initializeBottomSheet(){
        final LinearLayout bottomSheet = findViewById(R.id.bottom_sheet_layout);
        View noteColor = bottomSheet.findViewById(R.id.note_color);
        //BottomSheetBehavior<LinearLayout> behavior = BottomSheetBehavior.from(bottomSheet);
        GradientDrawable drawable = (GradientDrawable) noteColor.getBackground();
        // ImageViews for color selection
        final ImageView imageColor1 = bottomSheet.findViewById(R.id.im_color_1);
        final ImageView imageColor2 = bottomSheet.findViewById(R.id.im_color_2);
        final ImageView imageColor3 = bottomSheet.findViewById(R.id.im_color_3);
        final ImageView imageColor4 = bottomSheet.findViewById(R.id.im_color_4);
        final ImageView imageColor5 = bottomSheet.findViewById(R.id.im_color_5);
        // Default color setting
        color = "#333333";
        drawable.setColor(Color.parseColor(color));
        // Color selection logic
        imageColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = "#333333";
                imageColor1.setImageResource(R.drawable.ic_done);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);

                drawable.setColor(Color.parseColor(color));
            }
        });
        imageColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = "#FF7F50";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(R.drawable.ic_done);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);

                drawable.setColor(Color.parseColor(color));
            }
        });
        imageColor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = "#1F216F";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(R.drawable.ic_done);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);

                drawable.setColor(Color.parseColor(color));
            }
        });
        imageColor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = "#308EA0";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(R.drawable.ic_done);
                imageColor5.setImageResource(0);

                drawable.setColor(Color.parseColor(color));
            }
        });
        imageColor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = "#673AB7";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(R.drawable.ic_done);

                drawable.setColor(Color.parseColor(color));
            }
        });
        // If an existing note is being viewed or updated, set the selected color
        if(alreadyNote != null && alreadyNote.getColor() != null){
            switch (alreadyNote.getColor()){
                case "#333333":
                    imageColor1.performClick();
                    break;
                case "#FDBE3B":
                    imageColor2.performClick();
                    break;
                case "#FF4842":
                    imageColor3.performClick();
                    break;
                case "#3A52FC":
                    imageColor4.performClick();
                    break;
                case "#673AB7":
                    imageColor5.performClick();
                    break;
            }
        }
    }
}
