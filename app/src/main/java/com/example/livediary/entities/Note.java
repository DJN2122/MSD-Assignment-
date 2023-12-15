package com.example.livediary.entities;

// Import statements
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

// Annotation to declare this class as an Entity for Room with a specified table name 'notes'
@Entity(tableName = "notes")
public class Note implements Serializable {

    // Fields of the Note class with annotations for Room database

    @PrimaryKey(autoGenerate = true) // Primary key, auto-generated
    private int id;

    @ColumnInfo(name = "title") // Column named 'title'
    private String title;

    @ColumnInfo(name = "content") // Column named 'content'
    private String content;

    @ColumnInfo(name = "date") // Column named 'date'
    private String date;

    @ColumnInfo(name = "color") // Column named 'color'
    private String color;

    // Standard getters and setters for each field

    public int getId() {
        // Getter for the 'id' field
        return id;
    }

    public void setId(int id) {
        // Setter for the 'id' field
        this.id = id;
    }

    public String getTitle() {
        // Getter for the 'title' field
        return title;
    }

    public void setTitle(String title) {
        // Setter for the 'title' field
        this.title = title;
    }

    public String getContent() {
        // Getter for the 'content' field
        return content;
    }

    public void setContent(String content) {
        // Setter for the 'content' field
        this.content = content;
    }

    public String getDate() {
        // Getter for the 'date' field
        return date;
    }

    public void setDate(String date) {
        // Setter for the 'date' field
        this.date = date;
    }

    public String getColor() {
        // Getter for the 'color' field
        return color;
    }

    public void setColor(String color) {
        // Setter for the 'color' field
        this.color = color;
    }
}
