package wgu.lschol1.c196;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {

    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();
    EditText termNameText;
    EditText termStartText;
    EditText termEndText;
    DatePickerDialog.OnDateSetListener termStartPicker;
    DatePickerDialog.OnDateSetListener termEndPicker;

    public static final Integer TERM_ID = null;
    public static final String TERM_NAME = "";
    public static final String TERM_START = "";
    public static final String TERM_END = "";

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
                /* TODO
                *   Make a bundle instead of multiple intents for my values*/
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(termNameText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String name = termNameText.getText().toString();
                    String start = termStartText.getText().toString();
                    String end = termEndText.getText().toString();

                    Log.d("name", name);
                    Log.d("start", start);
                    Log.d("end", end);

                    replyIntent.putExtra(TERM_NAME, name);
                    replyIntent.putExtra(TERM_START, start);
                    replyIntent.putExtra(TERM_END, end);

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        // set date submission listeners
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

        // set click listeners on date text
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
}
