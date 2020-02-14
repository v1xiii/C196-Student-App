package wgu.lschol1.c196.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import wgu.lschol1.c196.database.AssessmentEntity;
import wgu.lschol1.c196.database.AssessmentRepository;

public class AssessmentsViewModel extends AndroidViewModel {

    private AssessmentRepository mRepository;

    private LiveData<List<AssessmentEntity>> mAllAssessments;

    public AssessmentsViewModel (Application application) {
        super(application);
        mRepository = new AssessmentRepository(application);
        mAllAssessments = mRepository.getAllAssessments();
    }

    public LiveData<List<AssessmentEntity>> getAllAssessments() {
        return mAllAssessments;
    }

    public void insert(AssessmentEntity assessment) {
        mRepository.insert(assessment);
    }

    public void delete(AssessmentEntity assessment){
        mRepository.delete(assessment);
    }
}