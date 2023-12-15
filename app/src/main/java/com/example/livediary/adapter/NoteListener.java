package com.example.livediary.adapter;

import com.example.livediary.entities.Note;

public interface NoteListener {
    void onNoteClicked(Note note, int position);
}
