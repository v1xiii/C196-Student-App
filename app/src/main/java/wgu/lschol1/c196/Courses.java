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

import wgu.lschol1.c196.adapters.CoursesAdapter;
import wgu.lschol1.c196.database.CourseEntity;
import wgu.lschol1.c196.viewmodels.CoursesViewModel;

public class Courses extends AppCompatActivity {

    private CoursesViewModel mCoursesViewModel;
    public static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_courses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView coursesRecyclerView = findViewById(R.id.courses_recyclerview);
        final CoursesAdapter adapter = new CoursesAdapter(this);
        coursesRecyclerView.setAdapter(adapter);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCoursesViewModel = new ViewModelProvider(this).get(CoursesViewModel.class);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null){
            if (extras.containsKey("TERM_ID")) { // courses by term ID (from term details)
                int termId = extras.getInt("TERM_ID", 0);
                System.out.println("pulling courses for term - " + termId);

                mCoursesViewModel.getCoursesByTermId(termId).observe(this, new Observer<List<CourseEntity>>() {
                    @Override
                    public void onChanged(@Nullable final List<CourseEntity> courses) {
                        adapter.setCourses(courses);
                    }
                });
            }
        } else { // all courses (from main page)
            mCoursesViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
                @Override
                public void onChanged(@Nullable final List<CourseEntity> courses) {
                    adapter.setCourses(courses);
                }
            });
        }
    }

    public void openCourseDetailsPage(View view) {
        startActivityForResult(new Intent(Courses.this, CourseDetails.class), NEW_COURSE_ACTIVITY_REQUEST_CODE);
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

        if (requestCode == NEW_COURSE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) { // when data comes back from the course details activity (save)
            int courseId = Objects.requireNonNull(extras).getInt(CourseDetails.COURSE_ID,0);
            String courseName = extras.getString(CourseDetails.COURSE_NAME);
            String courseStart = extras.getString(CourseDetails.COURSE_START);
            String courseEnd = extras.getString(CourseDetails.COURSE_END);
            String courseStatus = extras.getString(CourseDetails.COURSE_STATUS);
            int courseTerm = extras.getInt(CourseDetails.COURSE_TERM,0);
            String courseMentor = extras.getString(CourseDetails.COURSE_MENTOR);

            System.out.println(courseTerm);

            CourseEntity course = new CourseEntity(courseId, courseName, courseStart, courseEnd, courseStatus, courseTerm, courseMentor);
            mCoursesViewModel.insert(course);

        } else {
            Toast.makeText(getApplicationContext(),R.string.empty_not_saved,Toast.LENGTH_LONG).show();
        }
    }
}