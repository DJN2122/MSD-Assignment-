package com.example.livediary.database;

// Import statements
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.livediary.entities.Note;

// Annotation to define this class as a Room Database with a single entity (Note) and version number 1
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    // Abstract method that returns an instance of NoteDao
    public abstract NoteDao dao();

    // Static instance variable for implementing a singleton pattern
    private static volatile NoteDatabase instance;

    // Static synchronized method to get an instance of the database
    public static synchronized NoteDatabase getInstance(Context context) {
        // Check if an instance already exists
        if (instance == null) {
            // If not, create a new instance using Room's database builder
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, "note_database")
                    // Use fallbackToDestructiveMigration in case of schema changes
                    .fallbackToDestructiveMigration()
                    .build(); // Build the database
        }
        // Return the instance of the database
        return instance;
    }
}
