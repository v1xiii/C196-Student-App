package wgu.lschol1.c196;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class CourseDetails extends AppCompatActivity {

    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();
    EditText courseNameText;
    EditText courseStartText;
    EditText courseEndText;
    DatePickerDialog.OnDateSetListener courseStartPicker;
    DatePickerDialog.OnDateSetListener courseEndPicker;

    public static final String COURSE_ID = "courseId";
    public static final String COURSE_NAME = "courseName";
    public static final String COURSE_START = "courseStart";
    public static final String COURSE_END = "courseEnd";

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

                    extras.putInt(COURSE_ID, courseId);
                    extras.putString(COURSE_NAME, name);
                    extras.putString(COURSE_START, start);
                    extras.putString(COURSE_END, end);

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

        setCourseDetails();
        setStatusSpinner();
        setTermSpinner();
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

    private void setCourseDetails() {
        Intent intent = getIntent();

        if (intent.hasExtra("courseEntity")){
            TextView courseTitle = findViewById(R.id.course_name);
            TextView courseStart = findViewById(R.id.course_start);
            TextView courseEnd = findViewById(R.id.course_end);

            courseEntity = (CourseEntity) getIntent().getSerializableExtra("courseEntity");
            courseId = Objects.requireNonNull(courseEntity).getId();

            courseTitle.setText(courseEntity.getTitle());
            courseStart.setText(courseEntity.getStart());
            courseEnd.setText(courseEntity.getEnd());
        }
    }

    public void deleteCourse(View view) {
        CoursesViewModel mCoursesViewModel = new ViewModelProvider(this).get(CoursesViewModel.class);
        mCoursesViewModel.delete(courseEntity);

        Intent replyIntent = new Intent();
        setResult(RESULT_CANCELED, replyIntent);
        finish();
    }

    private void setStatusSpinner(){
        Spinner spin = (Spinner) findViewById(R.id.course_status);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }

    private void setTermSpinner(){
        // get the term data and create an array of strings, add array to last parameter of new ArrayAdapter.
        // type converter to change objects to strings???
        // comment out the final block

        Spinner spin = (Spinner) findViewById(R.id.term);
        //ArrayAdapter<TermEntity> adapter = new ArrayAdapter<TermEntity>(this, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        termViewModel = new ViewModelProvider(this).get(TermsViewModel.class);
        termViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(@Nullable final List<TermEntity> terms) {
               // adapter.addAll(terms); // wish I could just populate the spinner with objects and display only the title property...

                String title = "";
                for(TermEntity current : Objects.requireNonNull(terms)) { // but instead I have to do this for some reason
                    title = current.getTitle();
                    adapter.add(title);
                }
            }
        });
    }
}