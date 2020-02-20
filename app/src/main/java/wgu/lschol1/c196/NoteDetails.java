package wgu.lschol1.c196;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import wgu.lschol1.c196.database.NoteEntity;
import wgu.lschol1.c196.viewmodels.NotesViewModel;

public class NoteDetails extends AppCompatActivity {

    EditText noteNameText;
    
    EditText noteBodyText;
    EditText noteDueDateText;
    DatePickerDialog.OnDateSetListener noteBodyTextPicker;
    DatePickerDialog.OnDateSetListener noteDueDatePicker;

    Spinner noteTypeText;
    Spinner noteCourseId;

    public static final String NOTE_ID = "noteId";
    public static final String NOTE_NAME = "noteName";
    public static final String NOTE_BODY_TEXT = "noteBodyText";
    public static final String NOTE_COURSE = "noteCourse";

    private NoteEntity noteEntity;

    private int noteId = 0;
    private int noteCourse = 0;

    String[] types = { "Performance", "Objective" };

    @Override
    protected void onCreate(Bundle savedInstanceState) { // initialize the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noteEntity = (NoteEntity) getIntent().getSerializableExtra("noteEntity"); // get serialized note object from intent
        if (noteEntity == null){
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            if (extras.containsKey("COURSE_ID")){
                noteCourse = extras.getInt("COURSE_ID", 0);
            }
        }
        //System.out.println("loaded note has courseID of - " + noteEntity.getCourse());

        // ID is global
        noteNameText = findViewById(R.id.note_name);
        noteBodyText = findViewById(R.id.note_body_text);
        // course ID is set onCreate

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() { // FAB functionality (save)
            @Override
            public void onClick(View view) { // sends field data back to notes to be saved
                Intent replyIntent = new Intent();
                Bundle extras = new Bundle();
                if (TextUtils.isEmpty(noteNameText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // ID is set globally
                    String name = noteNameText.getText().toString();
                    String bodyText = noteBodyText.getText().toString();
                    int course = noteCourse;

                    extras.putInt(NOTE_ID, noteId);
                    extras.putString(NOTE_NAME, name);
                    extras.putString(NOTE_BODY_TEXT, bodyText);
                    extras.putInt(NOTE_COURSE, course);

                    System.out.println("Submitting note with courseId of - " + course);

                    replyIntent.putExtras(extras);

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        setNoteDetails();
    }

    private void setNoteDetails() { // set all of the note fields when a pre-existing note is loaded
        Intent intent = getIntent();

        if (intent.hasExtra("noteEntity")){
            NotesViewModel mNotesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

            TextView noteName = findViewById(R.id.note_name);
            TextView noteBodyText = findViewById(R.id.note_body_text);

            noteId = Objects.requireNonNull(noteEntity).getId();
            noteCourse = noteEntity.getCourse();

            // set inputs to this object's values
            noteName.setText(noteEntity.getName());
            noteBodyText.setText(noteEntity.getBodyText());
        }
    }

    public void deleteNote(View view) {
        NotesViewModel mNotesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        mNotesViewModel.delete(noteEntity);

        Intent replyIntent = new Intent();
        setResult(RESULT_CANCELED, replyIntent);
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}

    public void openNotesPage(View view){
        Intent intent = new Intent(NoteDetails.this, Notes.class);

        intent.putExtra("noteId", noteEntity.getId());

        startActivity(new Intent(NoteDetails.this, Notes.class));
    }
}