package wgu.lschol1.c196;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import wgu.lschol1.c196.adapters.NotesAdapter;
import wgu.lschol1.c196.database.NoteEntity;
import wgu.lschol1.c196.viewmodels.NotesViewModel;

public class Notes extends AppCompatActivity {

    private NotesViewModel mNotesViewModel;
    public static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView notesRecyclerView = findViewById(R.id.notes_recyclerview);
        final NotesAdapter adapter = new NotesAdapter(this);
        notesRecyclerView.setAdapter(adapter);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNotesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        courseId = getIntent().getExtras().getInt("COURSE_ID");
        System.out.println("pulling notes for course - " + courseId);
        mNotesViewModel.getNotesByCourseId(courseId).observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(@Nullable final List<NoteEntity> notes) {
                adapter.setNotes(notes);
            }
        });
    }

    public void openNoteDetailsPage(View view) {
        Intent intent = new Intent(Notes.this, NoteDetails.class);
        intent.putExtra("COURSE_ID", courseId);
        startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) { // when data comes back from an activity
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = null;

        if (data != null) {
            extras = data.getExtras();
            /*
            for (String key : extras.keySet()){
                Log.d("Bundle Debug", key + " = \"" + extras.get(key) + "\"");
            }
            */
        }

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) { // when data comes back from the note details activity (save)
            int noteId = Objects.requireNonNull(extras).getInt(NoteDetails.NOTE_ID,0);
            String noteName = extras.getString(NoteDetails.NOTE_NAME);
            String noteBodyText = extras.getString(NoteDetails.NOTE_BODY_TEXT);
            int noteCourse = extras.getInt(NoteDetails.NOTE_COURSE,0);

            System.out.println("Saving note with courseId of - " + noteCourse);

            NoteEntity note = new NoteEntity(noteId, noteName, noteBodyText, noteCourse);
            mNotesViewModel.insert(note);

        } else {
            Toast.makeText(getApplicationContext(),R.string.empty_not_saved,Toast.LENGTH_LONG).show();
        }
    }
}