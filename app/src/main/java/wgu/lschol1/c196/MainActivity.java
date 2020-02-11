package wgu.lschol1.c196;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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