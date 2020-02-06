package wgu.lschol1.c196.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CourseRepository {

    private CourseDao mCourseDao;
    private TermDao mTermDao;
    private LiveData<List<CourseEntity>> mAllCourses;

    public CourseRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mCourseDao = db.courseDao();
        mTermDao = db.termDao();
        mAllCourses = mCourseDao.getAllCourses();
    }

    public LiveData<List<CourseEntity>> getAllCourses() {
        return mAllCourses;
    }

    public void insert(CourseEntity course) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDao.insert(course);
        });
    }

    public void delete(CourseEntity course) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDao.delete(course);
        });
    }

    public TermEntity getTermById(int termId) {
        return mCourseDao.getTermById(termId);
    }
}