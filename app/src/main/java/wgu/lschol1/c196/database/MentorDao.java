package wgu.lschol1.c196.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MentorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MentorEntity mentor);

    @Delete
    void delete(MentorEntity mentorEntity);

    @Query("DELETE FROM mentors")
    void deleteAll();

    @Query("SELECT * FROM mentors ORDER BY id ASC")
    LiveData<List<MentorEntity>> getAllMentors();
}