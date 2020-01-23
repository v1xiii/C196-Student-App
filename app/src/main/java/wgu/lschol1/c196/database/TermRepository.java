package wgu.lschol1.c196.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TermRepository {

    private TermDao mTermDao;
    private LiveData<List<TermEntity>> mAllTerms;

    public TermRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mTermDao = db.termDao();
        mAllTerms = mTermDao.getAllTerms();
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return mAllTerms;
    }

    public void insert(TermEntity term) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.insert(term);
        });
    }

    public void delete(TermEntity term) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.delete(term);
        });
    }
}