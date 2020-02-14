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

import wgu.lschol1.c196.adapters.AssessmentsAdapter;
import wgu.lschol1.c196.database.AssessmentEntity;
import wgu.lschol1.c196.viewmodels.AssessmentsViewModel;

public class Assessments extends AppCompatActivity {

    private AssessmentsViewModel mAssessmentsViewModel;
    public static final int NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE = 1;

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

        mAssessmentsViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(@Nullable final List<AssessmentEntity> assessments) {
                adapter.setAssessments(assessments);
            }
        });
    }

    public void openAssessmentDetailsPage(View view) {
        startActivityForResult(new Intent(Assessments.this, AssessmentDetails.class), NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE);
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

            System.out.println(assessmentCourse);

            AssessmentEntity assessment = new AssessmentEntity(assessmentId, assessmentName, assessmentGoalDate, assessmentDueDate, assessmentType, assessmentCourse);
            mAssessmentsViewModel.insert(assessment);

        } else {
            Toast.makeText(getApplicationContext(),R.string.empty_not_saved,Toast.LENGTH_LONG).show();
        }
    }
}