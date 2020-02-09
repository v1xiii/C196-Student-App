package wgu.lschol1.c196.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TermEntity.class, CourseEntity.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            /*
            databaseWriteExecutor.execute(() -> { // clears table and inserts test data, comment out to save manually entered data
                TermDao dao = INSTANCE.termDao();
                dao.deleteAll();

                TermEntity term = new TermEntity(0,"title1","start1","end1");
                dao.insert(term);
                term = new TermEntity(0,"title2","start2","end2");
                dao.insert(term);
            });


            databaseWriteExecutor.execute(() -> { // clears table and inserts test data, comment out to save manually entered data
                CourseDao dao = INSTANCE.courseDao();
                dao.deleteAll();

                CourseEntity course = new CourseEntity(0,"title1","start1","end1", "status1", 0);
                dao.insert(course);
                course = new CourseEntity(0,"title2","start2","end2", "status2", 1);
                dao.insert(course);
            });
            */
        }
    };
}