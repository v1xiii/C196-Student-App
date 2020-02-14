package wgu.lschol1.c196.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import wgu.lschol1.c196.database.AssessmentEntity;
import wgu.lschol1.c196.database.AssessmentRepository;

public class AssessmentDetailsViewModel extends AndroidViewModel {

    private AssessmentRepository mRepository;

    private LiveData<List<AssessmentEntity>> mAllAssessments;

    public AssessmentDetailsViewModel (Application application) {
        super(application);
        mRepository = new AssessmentRepository(application);
        mAllAssessments = mRepository.getAllAssessments();
    }

    LiveData<List<AssessmentEntity>> getAllAssessments() {
        return mAllAssessments;
    }

    public void insert(AssessmentEntity assessment) {
        mRepository.insert(assessment);
    }
}