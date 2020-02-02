package wgu.lschol1.c196.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

// this thing is basically just a regular object that simultaneously defines a database table
@Entity(tableName = "courses")
public class CourseEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String start;
    private String end;
    private String status;

    public CourseEntity(int id, String title, String start, String end, String status) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}