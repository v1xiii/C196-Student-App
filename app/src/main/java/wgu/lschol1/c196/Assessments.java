package wgu.lschol1.c196;

import android.app.AlarmManager;
import android.app.PendingIntent;
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

import wgu.lschol1.c196.adapters.AssessmentsAdapter;
import wgu.lschol1.c196.database.AssessmentEntity;
import wgu.lschol1.c196.viewmodels.AssessmentsViewModel;

public class Assessments extends AppCompatActivity {

    private AssessmentsViewModel mAssessmentsViewModel;
    public static final int NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE = 1;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_assessments);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView assessmentsRecyclerView = findViewById(R.id.assessments_recyclerview);
        final AssessmentsAdapter adapter = new AssessmentsAdapter(this);
        assessmentsRecyclerView.setAdapter(adapter);
        assessmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAssessmentsViewModel = new ViewModelProvider(this).get(AssessmentsViewModel.class);

        courseId = getIntent().getExtras().getInt("COURSE_ID");
        System.out.println("pulling assessments for course - " + courseId);
        mAssessmentsViewModel.getAssessmentsByCourseId(courseId).observe(this, new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(@Nullable final List<AssessmentEntity> assessments) {
                adapter.setAssessments(assessments);
            }
        });
    }

    public void openAssessmentDetailsPage(View view) {
        Intent intent = new Intent(Assessments.this, AssessmentDetails.class);
        intent.putExtra("COURSE_ID", courseId);
        startActivityForResult(intent, NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE);
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

        if (requestCode == NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) { // when data comes back from the assessment details activity (save)
            int assessmentId = Objects.requireNonNull(extras).getInt(AssessmentDetails.ASSESSMENT_ID,0);
            String assessmentName = extras.getString(AssessmentDetails.ASSESSMENT_NAME);
            String assessmentGoalDate = extras.getString(AssessmentDetails.ASSESSMENT_GOAL_DATE);
            String assessmentDueDate = extras.getString(AssessmentDetails.ASSESSMENT_DUE_DATE);
            String assessmentType = extras.getString(AssessmentDetails.ASSESSMENT_TYPE);
            int assessmentCourse = extras.getInt(AssessmentDetails.ASSESSMENT_COURSE,0);
            long assessmentGoalAlarm = extras.getLong(AssessmentDetails.ASSESSMENT_GOAL_ALARM, 0);
            long assessmentDueAlarm = extras.getLong(AssessmentDetails.ASSESSMENT_DUE_ALARM, 0);

            System.out.println("Saving assessment with courseId of - " + assessmentCourse);

            AssessmentEntity assessment = new AssessmentEntity(assessmentId, assessmentName, assessmentGoalDate, assessmentDueDate, assessmentType, assessmentCourse);
            mAssessmentsViewModel.insert(assessment);

            setDateNotification(assessmentName, assessmentGoalAlarm, "Goal");
            setDateNotification(assessmentName, assessmentDueAlarm, "Due");

        } else {
            Toast.makeText(getApplicationContext(),R.string.empty_not_saved,Toast.LENGTH_LONG).show();
        }
    }

    public void setDateNotification(String assessmentName, long assessmentAlarm, String type){
        //System.out.println(assessmentAlarm);
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("title","Today is your assessment "+type+" date!");
        intent.putExtra("message", "For assessment - "+assessmentName);
        int code = (int)(Math.random() * 999999999 + 1);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(this, code, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        if (alarm != null) {
            alarm.set(AlarmManager.RTC_WAKEUP, assessmentAlarm, pendingIntent);
        }
    }
}