package wgu.lschol1.c196.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import wgu.lschol1.c196.database.NoteEntity;
import wgu.lschol1.c196.database.NoteRepository;
import wgu.lschol1.c196.database.CourseEntity;

public class NotesViewModel extends AndroidViewModel {

    private NoteRepository mRepository;

    private LiveData<List<NoteEntity>> mAllNotes;

    public NotesViewModel (Application application) {
        super(application);
        mRepository = new NoteRepository(application);
        mAllNotes = mRepository.getAllNotes();
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return mAllNotes;
    }

    public LiveData<List<NoteEntity>> getNotesByCourseId(int id) {
        return mRepository.getNotesByCourseId(id);
    }

    public void insert(NoteEntity note) {
        mRepository.insert(note);
    }

    public void delete(NoteEntity note){
        mRepository.delete(note);
    }

    public LiveData<CourseEntity> getCourseById(int id) {
        return mRepository.getCourseById(id);
    }
}