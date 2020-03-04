package wgu.lschol1.c196;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layout_main);
        ConstraintSet set = new ConstraintSet();
        set.clone(layout);

        TextView title = new TextView(this);
        title.setText("C196 Term Tracker");
        title.setTextSize(18);
        title.setTypeface(null, Typeface.BOLD);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams( ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.endToEnd = ConstraintSet.PARENT_ID;
        layoutParams.startToStart = ConstraintSet.PARENT_ID;
        layoutParams.topToTop = ConstraintSet.PARENT_ID;
        layoutParams.topMargin = 128;

        title.setLayoutParams(layoutParams);
        layout.addView(title);
    }

    public void openTermsPage(View view) {
        startActivity(new Intent(MainActivity.this, Terms.class));
    }

    public void openCoursesPage(View view) {
        startActivity(new Intent(MainActivity.this, Courses.class));
    }

    public void openAssessmentsPage(View view) {
        startActivity(new Intent(MainActivity.this, Assessments.class));
    }

    public void openMentorsPage(View view) {
        startActivity(new Intent(MainActivity.this, Mentors.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}