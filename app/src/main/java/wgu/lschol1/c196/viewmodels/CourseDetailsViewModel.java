package wgu.lschol1.c196.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import wgu.lschol1.c196.database.CourseEntity;
import wgu.lschol1.c196.database.CourseRepository;
import wgu.lschol1.c196.database.TermEntity;

public class CourseDetailsViewModel extends AndroidViewModel {

    private CourseRepository mRepository;
    private LiveData<List<CourseEntity>> mAllCourses;
    private TermEntity mTerm;

    public CourseDetailsViewModel (Application application, int id) {
        super(application);
        mRepository = new CourseRepository(application);
        mAllCourses = mRepository.getAllCourses();
        mTerm = mRepository.getTermById(id);
    }

    LiveData<List<CourseEntity>> getAllCourses() {
        return mAllCourses;
    }

    public void insert(CourseEntity course) {
        mRepository.insert(course);
    }
}