package wgu.lschol1.c196.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import wgu.lschol1.c196.database.CourseEntity;
import wgu.lschol1.c196.database.CourseRepository;
import wgu.lschol1.c196.database.TermEntity;

public class CoursesViewModel extends AndroidViewModel {

    private CourseRepository mRepository;

    private LiveData<List<CourseEntity>> mAllCourses;

    public CoursesViewModel (Application application) {
        super(application);
        mRepository = new CourseRepository(application);
        mAllCourses = mRepository.getAllCourses();
    }

    public LiveData<List<CourseEntity>> getAllCourses() {
        return mAllCourses;
    }

    public LiveData<List<CourseEntity>> getCoursesByTermId(int id) {
        return mRepository.getCoursesByTermId(id);
    }

    public void insert(CourseEntity course) {
        mRepository.insert(course);
    }

    public void delete(CourseEntity course){
        mRepository.delete(course);
    }

    public LiveData<TermEntity> getTermById(int id) {
        return mRepository.getTermById(id);
    }
}