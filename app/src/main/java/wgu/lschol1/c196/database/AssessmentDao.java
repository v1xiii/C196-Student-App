package wgu.lschol1.c196.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssessmentEntity assessment);

    @Delete
    void delete(AssessmentEntity assessmentEntity);

    @Query("DELETE FROM assessments")
    void deleteAll();

    @Query("SELECT * FROM assessments WHERE id = :id")
    AssessmentEntity getAssessmentById(int id);

    @Query("SELECT * from assessments ORDER BY id ASC")
    LiveData<List<AssessmentEntity>> getAllAssessments();

    @Query("SELECT * FROM assessments WHERE course = :cid")
    LiveData<List<AssessmentEntity>> getAssessmentsByCourseId(int cid);

    @Query("SELECT * FROM courses WHERE id = :id")
    LiveData<CourseEntity> getCourseById(int id);
}