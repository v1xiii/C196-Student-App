package wgu.lschol1.c196.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TermEntity term);

    @Delete
    void delete(TermEntity termEntity);

    @Query("DELETE FROM terms")
    void deleteAll();

    @Query("SELECT * FROM terms ORDER BY id ASC")
    LiveData<List<TermEntity>> getAllTerms();
}