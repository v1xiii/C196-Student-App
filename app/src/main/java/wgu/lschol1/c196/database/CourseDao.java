package wgu.lschol1.c196.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseEntity course);

    @Delete
    void delete(CourseEntity courseEntity);

    @Query("DELETE FROM courses")
    void deleteAll();

    @Query("SELECT * FROM courses WHERE id = :id")
    CourseEntity getCourseById(int id);

    @Query("SELECT * from courses ORDER BY id ASC")
    LiveData<List<CourseEntity>> getAllCourses();

    @Query("SELECT * FROM terms WHERE id = :id")
    TermEntity getTermById(int id);
}