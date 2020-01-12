package wgu.lschol1.c196.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// this thing is basically just a regular object that simultaneously defines a database table
@Entity(tableName = "terms")
public class TermEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String start;
    private String end;
    private String title;

    public TermEntity(int id, String start, String end, String title) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.title = title;
    }

    public int getId() {
        return id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}