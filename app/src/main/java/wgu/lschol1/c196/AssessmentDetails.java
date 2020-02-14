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

import wgu.lschol1.c196.database.AssessmentEntity;
import wgu.lschol1.c196.database.CourseEntity;
import wgu.lschol1.c196.database.MentorEntity;
import wgu.lschol1.c196.database.TermEntity;
import wgu.lschol1.c196.viewmodels.AssessmentsViewModel;
import wgu.lschol1.c196.viewmodels.MentorsViewModel;
import wgu.lschol1.c196.viewmodels.TermsViewModel;

public class AssessmentDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText assessmentNameText;
    
    Calendar goalDateCalendar = Calendar.getInstance();
    Calendar dueDateCalendar = Calendar.getInstance();    
    EditText assessmentGoalDateText;
    EditText assessmentDueDateText;
    DatePickerDialog.OnDateSetListener assessmentGoalDatePicker;
    DatePickerDialog.OnDateSetListener assessmentDueDatePicker;
    
    Spinner assessmentTypeText;
    Spinner assessmentCourseId;

    public static final String ASSESSMENT_ID = "assessmentId";
    public static final String ASSESSMENT_NAME = "assessmentName";
    public static final String ASSESSMENT_GOAL_DATE = "assessmentGoalDate";
    public static final String ASSESSMENT_DUE_DATE = "assessmentDueDate";
    public static final String ASSESSMENT_TYPE = "assessmentType";
    public static final String ASSESSMENT_COURSE = "assessmentCourse";

    private AssessmentEntity assessmentEntity;
    private CourseEntity courseEntity;
    private int assessmentId = 0;

    private int assessmentCourse = 0;

    String[] types = { "Performance", "Objective" };

    @Override
    protected void onCreate(Bundle savedInstanceState) { // initialize the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assessmentEntity = (AssessmentEntity) getIntent().getSerializableExtra("assessmentEntity"); // get serialized assessment object from intent

        // ID is global
        assessmentNameText = findViewById(R.id.assessment_name);
        assessmentGoalDateText = findViewById(R.id.assessment_goal_date);
        assessmentDueDateText = findViewById(R.id.assessment_due_date);
        assessmentTypeText = findViewById(R.id.assessment_type);
        // term ID is global

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() { // FAB functionality (save)
            @Override
            public void onClick(View view) { // sends field data back to assessments to be saved
                Intent replyIntent = new Intent();
                Bundle extras = new Bundle();
                if (TextUtils.isEmpty(assessmentNameText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // ID is set globally
                    String name = assessmentNameText.getText().toString();
                    String goalDate = assessmentGoalDateText.getText().toString();
                    String dueDate = assessmentDueDateText.getText().toString();
                    String type = assessmentTypeText.getSelectedItem().toString();
                    // course ID is set globally

                    extras.putInt(ASSESSMENT_ID, assessmentId);
                    extras.putString(ASSESSMENT_NAME, name);
                    extras.putString(ASSESSMENT_GOAL_DATE, goalDate);
                    extras.putString(ASSESSMENT_DUE_DATE, dueDate);
                    extras.putString(ASSESSMENT_TYPE, type);
                    extras.putInt(ASSESSMENT_COURSE, assessmentCourse);

                    //System.out.println(assessmentCourse);

                    replyIntent.putExtras(extras);

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        assessmentGoalDatePicker = new DatePickerDialog.OnDateSetListener() { // start date selection listener
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) { // set calendar
                goalDateCalendar.set(Calendar.YEAR, year);
                goalDateCalendar.set(Calendar.MONTH, monthOfYear);
                goalDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateText("goalDate"); // update start date text to selection
            }
        };
        assessmentDueDatePicker = new DatePickerDialog.OnDateSetListener() { // end date selection listener
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) { // set calendar
                dueDateCalendar.set(Calendar.YEAR, year);
                dueDateCalendar.set(Calendar.MONTH, monthOfYear);
                dueDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateText("dueDate"); // update end date text to selection
            }
        };

        assessmentGoalDateText = findViewById(R.id.assessment_goal_date);
        assessmentDueDateText = findViewById(R.id.assessment_due_date);

        assessmentGoalDateText.setOnClickListener(new View.OnClickListener() { // display the start calendar when the date text is clicked
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AssessmentDetails.this, assessmentGoalDatePicker, goalDateCalendar.get(Calendar.YEAR), goalDateCalendar.get(Calendar.MONTH), goalDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        assessmentDueDateText.setOnClickListener(new View.OnClickListener() { // display the end calendar when the date text is clicked
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AssessmentDetails.this, assessmentDueDatePicker, dueDateCalendar.get(Calendar.YEAR), dueDateCalendar.get(Calendar.MONTH), dueDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        setTypeSpinner(); // populates the status spinner
        setCourseSpinner(); // populates the term spinner
    }

    private void updateText(String field) { // update "calendar closed" text to selection
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (field.equals("start")){
            assessmentGoalDateText.setText(sdf.format(goalDateCalendar.getTime()));
        } else {
            assessmentDueDateText.setText(sdf.format(dueDateCalendar.getTime()));
        }
    }

    private void setTypeSpinner(){ // populates the status spinner
        Spinner spin = findViewById(R.id.assessment_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }

    private void setCourseSpinner(){ // populates the term spinner
        Spinner spin = (Spinner) findViewById(R.id.assessment_course);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter<TermEntity> adapter = new ArrayAdapter<TermEntity>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        TermsViewModel termViewModel = new ViewModelProvider(this).get(TermsViewModel.class);
        termViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(@Nullable final List<TermEntity> terms) {
                adapter.addAll(Objects.requireNonNull(terms));
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) { // on selection of spinner item, do things
        CourseEntity course = (CourseEntity) parent.getItemAtPosition(pos);
        assessmentCourse = course.getId();
    }
    public void onNothingSelected(AdapterView<?> parent) {}

    private void setAssessmentDetails() { // set all of the assessment fields when a pre-existing assessment is loaded
        Intent intent = getIntent();

        if (intent.hasExtra("assessmentEntity")){
            AssessmentsViewModel mAssessmentsViewModel = new ViewModelProvider(this).get(AssessmentsViewModel.class);

            TextView assessmentName = findViewById(R.id.assessment_name);
            TextView assessmentGoalDate = findViewById(R.id.assessment_goal_date);
            TextView assessmentDueDate = findViewById(R.id.assessment_due_date);
            Spinner assessmentType = findViewById(R.id.assessment_type);
            Spinner assessmentCourseSpinner = findViewById(R.id.assessment_course);

            assessmentId = Objects.requireNonNull(assessmentEntity).getId();
            assessmentCourse = assessmentEntity.getCourse();

            // set inputs to this object's values
            assessmentName.setText(assessmentEntity.getName());
            assessmentGoalDate.setText(assessmentEntity.getGoalDate());
            assessmentDueDate.setText(assessmentEntity.getDueDate());
            assessmentType.setSelection(getSpinnerIndex(assessmentType, assessmentEntity.getType()));
            mAssessmentsViewModel.getCourseById(assessmentEntity.getCourse()).observe(this, new Observer<CourseEntity>() {
                @Override
                public void onChanged(@Nullable final CourseEntity course) {
                    assessmentCourseSpinner.setSelection(getSpinnerIndex(assessmentCourseSpinner, Objects.requireNonNull(course).getTitle()));
                }
            });
        }
    }

    private int getSpinnerIndex(Spinner spinner, String myString) { // get the index needed to set a spinner to the correct item on load
        int index = 0;
        System.out.println(spinner.getCount() + " - " + myString);
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().trim().equals(myString.trim())) {
                index = i;
            }
        }
        return index;
    }

    public void deleteAssessment(View view) {
        AssessmentsViewModel mAssessmentsViewModel = new ViewModelProvider(this).get(AssessmentsViewModel.class);
        mAssessmentsViewModel.delete(assessmentEntity);

        Intent replyIntent = new Intent();
        setResult(RESULT_CANCELED, replyIntent);
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}

    public void openAssessmentsPage(View view){
        Intent intent = new Intent(AssessmentDetails.this, Assessments.class);

        intent.putExtra("assessmentId", assessmentEntity.getId());

        startActivity(new Intent(AssessmentDetails.this, Assessments.class));
    }
}