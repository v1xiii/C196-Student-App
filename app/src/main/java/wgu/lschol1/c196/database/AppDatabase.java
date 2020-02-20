package wgu.lschol1.c196.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TermEntity.class, CourseEntity.class, MentorEntity.class, AssessmentEntity.class, NoteEntity.class}, version = 9, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract MentorDao mentorDao();
    public abstract AssessmentDao assessmentDao();
    public abstract NoteDao noteDao();

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

            databaseWriteExecutor.execute(() -> { // clears table and inserts test data, comment out to save manually entered data
                TermDao dao = INSTANCE.termDao();
                //dao.deleteAll();
/*
                TermEntity term = new TermEntity(0,"Spring","02/01/20","05/30/20");
                dao.insert(term);
                term = new TermEntity(0,"Summer","06/01/20","08/31/20");
                dao.insert(term);
                term = new TermEntity(0,"Fall","09/01/20","12/31/20");
                dao.insert(term);
 */
            });

            databaseWriteExecutor.execute(() -> { // clears table and inserts test data, comment out to save manually entered data
                MentorDao dao = INSTANCE.mentorDao();
                //dao.deleteAll();
/*
                MentorEntity mentor = new MentorEntity(0,"Robert McNamara","111-111-1111","robert.mcnamara@wgu.edu");
                dao.insert(mentor);
                mentor = new MentorEntity(0,"Erik Anderson","222-222-2222","erik.anderson@wgu.edu");
                dao.insert(mentor);
 */
            });

            databaseWriteExecutor.execute(() -> { // clears table and inserts test data, comment out to save manually entered data
                CourseDao dao = INSTANCE.courseDao();
                //dao.deleteAll();
                /*
                CourseEntity course = new CourseEntity(0,"Biology","02/01/20","05/30/20", "In Progress", 0);
                dao.insert(course);
                course = new CourseEntity(0,"Algebra","02/01/20","05/30/20", "Completed", 0);
                dao.insert(course);
                course = new CourseEntity(0,"History","02/01/20","05/30/20", "Dropped", 0);
                dao.insert(course);
                course = new CourseEntity(0,"English","02/01/20","05/30/20", "Plan to Take", 0);
                dao.insert(course);
                 */
            });
        }
    };
}