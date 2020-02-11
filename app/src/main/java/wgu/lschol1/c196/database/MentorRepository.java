package wgu.lschol1.c196.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MentorRepository {

    private MentorDao mMentorDao;
    private LiveData<List<MentorEntity>> mAllMentors;

    public MentorRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mMentorDao = db.mentorDao();
        mAllMentors = mMentorDao.getAllMentors();
    }

    public LiveData<List<MentorEntity>> getAllMentors() {
        return mAllMentors;
    }

    public void insert(MentorEntity mentor) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mMentorDao.insert(mentor);
        });
    }

    public void delete(MentorEntity mentor) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mMentorDao.delete(mentor);
        });
    }
}