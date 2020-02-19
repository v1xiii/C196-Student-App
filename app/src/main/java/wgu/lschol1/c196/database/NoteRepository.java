package wgu.lschol1.c196.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private NoteDao mNoteDao;
    private LiveData<List<NoteEntity>> mAllNotes;

    public NoteRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mNoteDao = db.noteDao();
        mAllNotes = mNoteDao.getAllNotes();
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return mAllNotes;
    }

    public LiveData<List<NoteEntity>> getNotesByCourseId(int courseId) {
        return mNoteDao.getNotesByCourseId(courseId);
    }

    public void insert(NoteEntity note) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.insert(note);
        });
    }

    public void delete(NoteEntity note) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.delete(note);
        });
    }

    public LiveData<CourseEntity> getCourseById(int courseId) {
        return mNoteDao.getCourseById(courseId);
    }
}