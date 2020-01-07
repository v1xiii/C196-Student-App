package wgu.lschol1.c196.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(TermEntity termEntity);

    @Delete
    void deleteTerm(TermEntity termEntity);

    @Query("SELECT * FROM terms WHERE id = :id")
    TermEntity getTermById(int id);
}