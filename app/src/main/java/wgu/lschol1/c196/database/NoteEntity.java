package wgu.lschol1.c196.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

// this thing is basically just a regular object that simultaneously defines a database table
@Entity(tableName = "Notes")
public class NoteEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String bodyText;
    private int course;

    public NoteEntity(int id, String name, String bodyText, int course) {
        this.id = id;
        this.name = name;
        this.bodyText = bodyText;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}