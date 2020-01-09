package wgu.lschol1.c196.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// this thing is where you put your queries, and define functions that you can use to run them
@Dao
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(TermEntity termEntity);

    @Update
    void updateTerm(TermEntity termEntity);

    @Delete
    void deleteTerm(TermEntity termEntity);

    @Query("SELECT * FROM terms WHERE id = :id")
    TermEntity getTermById(int id);

    @Query("SELECT * FROM terms ORDER BY id ASC")
    LiveData<List<TermEntity>> getAllTerms();
}