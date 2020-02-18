package wgu.lschol1.c196;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import wgu.lschol1.c196.database.TermEntity;
import wgu.lschol1.c196.viewmodels.TermsViewModel;

public class TermDetails extends AppCompatActivity {

    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();
    EditText termNameText;
    EditText termStartText;
    EditText termEndText;
    DatePickerDialog.OnDateSetListener termStartPicker;
    DatePickerDialog.OnDateSetListener termEndPicker;

    public static final String TERM_ID = "termId";
    public static final String TERM_NAME = "termName";
    public static final String TERM_START = "termStart";
    public static final String TERM_END = "termEnd";

    private TermEntity termEntity;
    private int termId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        termNameText = findViewById(R.id.term_name);
        termStartText = findViewById(R.id.term_start);
        termEndText = findViewById(R.id.term_end);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                Bundle extras = new Bundle();
                if (TextUtils.isEmpty(termNameText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String name = termNameText.getText().toString();
                    String start = termStartText.getText().toString();
                    String end = termEndText.getText().toString();

                    extras.putInt(TERM_ID, termId);
                    extras.putString(TERM_NAME, name);
                    extras.putString(TERM_START, start);
                    extras.putString(TERM_END, end);

                    replyIntent.putExtras(extras);

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        termStartPicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, monthOfYear);
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateText("start");
            }
        };
        termEndPicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, monthOfYear);
                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateText("end");
            }
        };

        termStartText = findViewById(R.id.term_start);
        termEndText = findViewById(R.id.term_end);

        termStartText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TermDetails.this, termStartPicker, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        termEndText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TermDetails.this, termEndPicker, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        setTermDetails();
    }

    private void updateText(String field) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (field.equals("start")){
            termStartText.setText(sdf.format(startCalendar.getTime()));
        } else {
            termEndText.setText(sdf.format(endCalendar.getTime()));
        }
    }

    private void setTermDetails() {
        Intent intent = getIntent();

        if (intent.hasExtra("termEntity")){
            TextView termTitle = findViewById(R.id.term_name);
            TextView termStart = findViewById(R.id.term_start);
            TextView termEnd = findViewById(R.id.term_end);

            termEntity = (TermEntity) getIntent().getSerializableExtra("termEntity");
            termId = Objects.requireNonNull(termEntity).getId();

            termTitle.setText(termEntity.getTitle());
            termStart.setText(termEntity.getStart());
            termEnd.setText(termEntity.getEnd());
        }
    }

    public void deleteTerm(View view) {
        TermsViewModel mTermsViewModel = new ViewModelProvider(this).get(TermsViewModel.class);
        mTermsViewModel.delete(termEntity);

        Intent replyIntent = new Intent();
        setResult(RESULT_CANCELED, replyIntent);
        finish();
    }

    public void openCoursesPage(View view){
        Intent intent = new Intent(TermDetails.this, Courses.class);
        intent.putExtra("TERM_ID", termEntity.getId());
        startActivity(intent);
    }
}