package wgu.lschol1.c196.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import wgu.lschol1.c196.database.MentorEntity;
import wgu.lschol1.c196.database.MentorRepository;

public class MentorDetailsViewModel extends AndroidViewModel {

    private MentorRepository mRepository;

    private LiveData<List<MentorEntity>> mAllMentors;

    public MentorDetailsViewModel (Application application) {
        super(application);
        mRepository = new MentorRepository(application);
        mAllMentors = mRepository.getAllMentors();
    }

    LiveData<List<MentorEntity>> getAllMentors() {
        return mAllMentors;
    }

    public void insert(MentorEntity mentor) {
        mRepository.insert(mentor);
    }
}