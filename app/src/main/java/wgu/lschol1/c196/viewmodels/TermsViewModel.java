package wgu.lschol1.c196.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import wgu.lschol1.c196.database.TermEntity;
import wgu.lschol1.c196.database.TermRepository;

public class TermsViewModel extends AndroidViewModel {

    private TermRepository mRepository;

    private LiveData<List<TermEntity>> mAllTerms;

    public TermsViewModel (Application application) {
        super(application);
        mRepository = new TermRepository(application);
        mAllTerms = mRepository.getAllTerms();
    }

    public LiveData<List<TermEntity>> getAllTerms() { return mAllTerms; }

    public void insert(TermEntity term) { mRepository.insert(term); }
}