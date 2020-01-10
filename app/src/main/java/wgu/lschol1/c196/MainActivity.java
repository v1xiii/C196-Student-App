package wgu.lschol1.c196;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import wgu.lschol1.c196.adapters.TermsAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView termsRecyclerView = findViewById(R.id.terms_recyclerview);
        final TermsAdapter adapter = new TermsAdapter(this);
        termsRecyclerView.setAdapter(adapter);
        termsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
