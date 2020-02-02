package wgu.lschol1.c196;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import wgu.lschol1.c196.database.CourseEntity;
import wgu.lschol1.c196.database.TermEntity;
import wgu.lschol1.c196.viewmodels.CoursesViewModel;
import wgu.lschol1.c196.viewmodels.TermsViewModel;

public class CourseDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();
    EditText courseNameText;
    EditText courseStartText;
    EditText courseEndText;
    DatePickerDialog.OnDateSetListener courseStartPicker;
    DatePickerDialog.OnDateSetListener courseEndPicker;
    Spinner courseStatusText;

    public static final String COURSE_ID = "courseId";
    public static final String COURSE_NAME = "courseName";
    public static final String COURSE_START = "courseStart";
    public static final String COURSE_END = "courseEnd";
    public static final String COURSE_STATUS = "courseStatus";

    private CourseEntity courseEntity;
    private int courseId = 0;

    String[] statuses = { "In Progress", "Completed", "Dropped", "Plan to Take" };

    private TermsViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        courseNameText = findViewById(R.id.course_name);
        courseStartText = findViewById(R.id.course_start);
        courseEndText = findViewById(R.id.course_end);
        courseStatusText = findViewById(R.id.course_status);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                Bundle extras = new Bundle();
                if (TextUtils.isEmpty(courseNameText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String name = courseNameText.getText().toString();
                    String start = courseStartText.getText().toString();
                    String end = courseEndText.getText().toString();
                    String status = courseStatusText.getSelectedItem().toString();

                    extras.putInt(COURSE_ID, courseId);
                    extras.putString(COURSE_NAME, name);
                    extras.putString(COURSE_START, start);
                    extras.putString(COURSE_END, end);
                    extras.putString(COURSE_STATUS, status);

                    replyIntent.putExtras(extras);

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        courseStartPicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, monthOfYear);
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateText("start");
            }
        };
        courseEndPicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, monthOfYear);
                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateText("end");
            }
        };

        courseStartText = findViewById(R.id.course_start);
        courseEndText = findViewById(R.id.course_end);

        courseStartText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseDetails.this, courseStartPicker, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        courseEndText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CourseDetails.this, courseEndPicker, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        setStatusSpinner();
        setTermSpinner();
        setCourseDetails();
    }

    private void updateText(String field) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (field.equals("start")){
            courseStartText.setText(sdf.format(startCalendar.getTime()));
        } else {
            courseEndText.setText(sdf.format(endCalendar.getTime()));
        }
    }

    private void setStatusSpinner(){
        Spinner spin = findViewById(R.id.course_status);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }

    private void setTermSpinner(){
        Spinner spin = (Spinner) findViewById(R.id.term);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<TermEntity> adapter = new ArrayAdapter<TermEntity>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        termViewModel = new ViewModelProvider(this).get(TermsViewModel.class);
        termViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(@Nullable final List<TermEntity> terms) {
                adapter.addAll(Objects.requireNonNull(terms));
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        /*
        TODO
            Make term spinner save term id to course in DB
            Create mentor entity and related junk
            Make mentor spinner populate and save
            Need to figure out how to specify what is what in this onItemSelected() function, will need to play nice with Mentor object as well. (if this thing is of type, do x, else y )
         */

        TermEntity test = (TermEntity) parent.getItemAtPosition(pos);
        System.out.println(test.getTitle());
    }
    public void onNothingSelected(AdapterView<?> parent) {}

    private void setCourseDetails() {
        Intent intent = getIntent();

        if (intent.hasExtra("courseEntity")){
            TextView courseTitle = findViewById(R.id.course_name);
            TextView courseStart = findViewById(R.id.course_start);
            TextView courseEnd = findViewById(R.id.course_end);
            Spinner courseStatus = findViewById(R.id.course_status);

            courseEntity = (CourseEntity) getIntent().getSerializableExtra("courseEntity");
            courseId = Objects.requireNonNull(courseEntity).getId();

            courseTitle.setText(courseEntity.getTitle());
            courseStart.setText(courseEntity.getStart());
            courseEnd.setText(courseEntity.getEnd());
            courseStatus.setSelection(getSpinnerIndex(courseStatus, courseEntity.getStatus()));
        }
    }

    private int getSpinnerIndex(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().trim().equals(myString.trim())) {
                index = i;
            }
        }
        return index;
    }

    public void deleteCourse(View view) {
        CoursesViewModel mCoursesViewModel = new ViewModelProvider(this).get(CoursesViewModel.class);
        mCoursesViewModel.delete(courseEntity);

        Intent replyIntent = new Intent();
        setResult(RESULT_CANCELED, replyIntent);
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}