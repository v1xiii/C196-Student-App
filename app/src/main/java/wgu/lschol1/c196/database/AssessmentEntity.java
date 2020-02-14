package wgu.lschol1.c196.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

// this thing is basically just a regular object that simultaneously defines a database table
@Entity(tableName = "assessments")
public class AssessmentEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String goalDate;
    private String dueDate;
    private String type;
    private int course;

    public AssessmentEntity(int id, String name, String goalDate, String dueDate, String type, int course) {
        this.id = id;
        this.name = name;
        this.goalDate = goalDate;
        this.dueDate = dueDate;
        this.type = type;
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

    public String getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(String goalDate) {
        this.goalDate = goalDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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