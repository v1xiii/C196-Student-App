package wgu.lschol1.c196.database;

/*
public class TermRepository {
    private TermDao mTermDao;
    private LiveData<List<TermEntity>> mAllTerms;

    TermRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mTermDao = db.termDao();
        mAllTerms = mTermDao.getAllTerms();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<TermEntity>> getAllTerms() {
        return mAllTerms;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(final TermEntity termEntity) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.insertTerm(termEntity);
        });
    }
}
*/

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TermRepository {

    private TermDao mTermDao;
    private LiveData<List<TermEntity>> mAllTerms;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public TermRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mTermDao = db.termDao();
        mAllTerms = mTermDao.getAllTerms();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<TermEntity>> getAllTerms() {
        return mAllTerms;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(TermEntity term) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.insert(term);
        });
    }
}