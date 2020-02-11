package wgu.lschol1.c196.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import wgu.lschol1.c196.database.MentorEntity;
import wgu.lschol1.c196.database.MentorRepository;

public class MentorsViewModel extends AndroidViewModel {

    private MentorRepository mRepository;

    private LiveData<List<MentorEntity>> mAllMentors;

    public MentorsViewModel (Application application) {
        super(application);
        mRepository = new MentorRepository(application);
        mAllMentors = mRepository.getAllMentors();
    }

    public LiveData<List<MentorEntity>> getAllMentors() {
        return mAllMentors;
    }

    public void insert(MentorEntity mentor) {
        mRepository.insert(mentor);
    }

    public void delete(MentorEntity mentor){
        mRepository.delete(mentor);
    }
}