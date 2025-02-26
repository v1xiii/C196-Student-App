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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import wgu.lschol1.c196.database.AssessmentEntity;
import wgu.lschol1.c196.viewmodels.AssessmentsViewModel;

public class AssessmentDetails extends AppCompatActivity {

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
    public static final String ASSESSMENT_GOAL_ALARM = "assessmentGoalAlarm";
    public static final String ASSESSMENT_DUE_ALARM = "assessmentDueAlarm";

    private AssessmentEntity assessmentEntity;

    private int assessmentId = 0;
    private int assessmentCourse = 0;

    //String[] types = { "Performance", "Objective" };
    ArrayList<String> types = new ArrayList<>(Arrays.asList("Performance", "Objective"));

    @Override
    protected void onCreate(Bundle savedInstanceState) { // initialize the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assessmentEntity = (AssessmentEntity) getIntent().getSerializableExtra("assessmentEntity"); // get serialized assessment object from intent

        if (assessmentEntity == null){
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            if (extras.containsKey("COURSE_ID")){
                assessmentCourse = extras.getInt("COURSE_ID", 0);
            }
        }
        //System.out.println("loaded assessment has courseID of - " + assessmentEntity.getCourse());

        // ID is global
        assessmentNameText = findViewById(R.id.assessment_name);
        assessmentGoalDateText = findViewById(R.id.assessment_goal_date);
        assessmentDueDateText = findViewById(R.id.assessment_due_date);
        assessmentTypeText = findViewById(R.id.assessment_type);
        // course ID is set onCreate

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
                    int course = assessmentCourse;
                    long alarmGoalTime = goalDateCalendar.getTimeInMillis();
                    long alarmDueTime = dueDateCalendar.getTimeInMillis();

                    extras.putInt(ASSESSMENT_ID, assessmentId);
                    extras.putString(ASSESSMENT_NAME, name);
                    extras.putString(ASSESSMENT_GOAL_DATE, goalDate);
                    extras.putString(ASSESSMENT_DUE_DATE, dueDate);
                    extras.putString(ASSESSMENT_TYPE, type);
                    extras.putInt(ASSESSMENT_COURSE, course);
                    extras.putLong(ASSESSMENT_GOAL_ALARM, alarmGoalTime);
                    extras.putLong(ASSESSMENT_DUE_ALARM, alarmDueTime);

                    //System.out.println("Submitting assessment with courseId of - " + course);

                    replyIntent.putExtras(extras);

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        assessmentGoalDatePicker = new DatePickerDialog.OnDateSetListener() { // goal date selection listener
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) { // set calendar
                goalDateCalendar.set(Calendar.YEAR, year);
                goalDateCalendar.set(Calendar.MONTH, monthOfYear);
                goalDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateText("goalDate"); // update goal date text to selection
            }
        };
        assessmentDueDatePicker = new DatePickerDialog.OnDateSetListener() { // due date selection listener
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) { // set calendar
                dueDateCalendar.set(Calendar.YEAR, year);
                dueDateCalendar.set(Calendar.MONTH, monthOfYear);
                dueDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateText("dueDate"); // update due date text to selection
            }
        };

        setCalendars();

        assessmentGoalDateText = findViewById(R.id.assessment_goal_date);
        assessmentDueDateText = findViewById(R.id.assessment_due_date);

        assessmentGoalDateText.setOnClickListener(new View.OnClickListener() { // display the goal date calendar when the date text is clicked
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AssessmentDetails.this, assessmentGoalDatePicker, goalDateCalendar.get(Calendar.YEAR), goalDateCalendar.get(Calendar.MONTH), goalDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        assessmentDueDateText.setOnClickListener(new View.OnClickListener() { // display the due date calendar when the date text is clicked
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AssessmentDetails.this, assessmentDueDatePicker, dueDateCalendar.get(Calendar.YEAR), dueDateCalendar.get(Calendar.MONTH), dueDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        setTypeSpinner(); // populates the type spinner
        setAssessmentDetails();
    }

    private void updateText(String field) { // update "calendar closed" text to selection
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (field.equals("goalDate")){
            assessmentGoalDateText.setText(sdf.format(goalDateCalendar.getTime()));
        } else {
            assessmentDueDateText.setText(sdf.format(dueDateCalendar.getTime()));
        }
    }

    private void setCalendars(){
        if (assessmentEntity != null) {
            String[] goalDateValues = assessmentEntity.getGoalDate().split("/");
            goalDateValues[2] = "20" + goalDateValues[2];
            String[] dueDateValues = assessmentEntity.getDueDate().split("/");
            dueDateValues[2] = "20" + dueDateValues[2];

            goalDateCalendar.set(Integer.parseInt(goalDateValues[2]), Integer.parseInt(goalDateValues[0])-1, Integer.parseInt(goalDateValues[1]));
            dueDateCalendar.set(Integer.parseInt(dueDateValues[2]), Integer.parseInt(dueDateValues[0])-1, Integer.parseInt(dueDateValues[1]));
        }
    }

    private void setTypeSpinner(){ // populates the type spinner
        Spinner spin = findViewById(R.id.assessment_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }

    private void setAssessmentDetails() { // set all of the assessment fields when a pre-existing assessment is loaded
        Intent intent = getIntent();

        if (intent.hasExtra("assessmentEntity")){
            AssessmentsViewModel mAssessmentsViewModel = new ViewModelProvider(this).get(AssessmentsViewModel.class);

            TextView assessmentName = findViewById(R.id.assessment_name);
            TextView assessmentGoalDate = findViewById(R.id.assessment_goal_date);
            TextView assessmentDueDate = findViewById(R.id.assessment_due_date);
            Spinner assessmentType = findViewById(R.id.assessment_type);

            assessmentId = Objects.requireNonNull(assessmentEntity).getId();
            assessmentCourse = assessmentEntity.getCourse();

            // set inputs to this object's values
            assessmentName.setText(assessmentEntity.getName());
            assessmentGoalDate.setText(assessmentEntity.getGoalDate());
            assessmentDueDate.setText(assessmentEntity.getDueDate());
            assessmentType.setSelection(getSpinnerIndex(assessmentType, assessmentEntity.getType()));
        }
    }

    private int getSpinnerIndex(Spinner spinner, String myString) { // get the index needed to set a spinner to the correct item on load
        int index = 0;
        //System.out.println(spinner.getCount() + " - " + myString);
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

    public void openNotesPage(View view){
        Intent intent = new Intent(AssessmentDetails.this, Notes.class);
        intent.putExtra("COURSE_ID", assessmentCourse);
        startActivity(intent);
    }
}