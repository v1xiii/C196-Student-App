package wgu.lschol1.c196.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AssessmentRepository {

    private AssessmentDao mAssessmentDao;
    private LiveData<List<AssessmentEntity>> mAllAssessments;

    public AssessmentRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mAssessmentDao = db.assessmentDao();
        mAllAssessments = mAssessmentDao.getAllAssessments();
    }

    public LiveData<List<AssessmentEntity>> getAllAssessments() {
        return mAllAssessments;
    }

    public void insert(AssessmentEntity assessment) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDao.insert(assessment);
        });
    }

    public void delete(AssessmentEntity assessment) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDao.delete(assessment);
        });
    }
}