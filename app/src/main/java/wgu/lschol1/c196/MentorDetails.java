package wgu.lschol1.c196;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import wgu.lschol1.c196.database.MentorEntity;
import wgu.lschol1.c196.viewmodels.MentorsViewModel;

public class MentorDetails extends AppCompatActivity {

    EditText mentorNameText;
    EditText mentorPhoneText;
    EditText mentorEmailText;

    public static final String MENTOR_ID = "mentorId";
    public static final String MENTOR_NAME = "mentorName";
    public static final String MENTOR_PHONE = "mentorPhone";
    public static final String MENTOR_EMAIL = "mentorEmail";

    private MentorEntity mentorEntity;
    private int mentorId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mentorNameText = findViewById(R.id.mentor_name);
        mentorPhoneText = findViewById(R.id.mentor_phone);
        mentorEmailText = findViewById(R.id.mentor_email);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                Bundle extras = new Bundle();
                if (TextUtils.isEmpty(mentorNameText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String name = mentorNameText.getText().toString();
                    String phone = mentorPhoneText.getText().toString();
                    String email = mentorEmailText.getText().toString();

                    extras.putInt(MENTOR_ID, mentorId);
                    extras.putString(MENTOR_NAME, name);
                    extras.putString(MENTOR_PHONE, phone);
                    extras.putString(MENTOR_EMAIL, email);

                    replyIntent.putExtras(extras);

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        setMentorDetails();
    }

    private void setMentorDetails() {
        Intent intent = getIntent();

        if (intent.hasExtra("mentorEntity")){
            TextView mentorName = findViewById(R.id.mentor_name);
            TextView mentorPhone = findViewById(R.id.mentor_phone);
            TextView mentorEmail = findViewById(R.id.mentor_email);

            mentorEntity = (MentorEntity) getIntent().getSerializableExtra("mentorEntity");
            mentorId = Objects.requireNonNull(mentorEntity).getId();

            mentorName.setText(mentorEntity.getName());
            mentorPhone.setText(mentorEntity.getPhone());
            mentorEmail.setText(mentorEntity.getEmail());
        }
    }

    public void deleteMentor(View view) {
        MentorsViewModel mMentorsViewModel = new ViewModelProvider(this).get(MentorsViewModel.class);
        mMentorsViewModel.delete(mentorEntity);

        Intent replyIntent = new Intent();
        setResult(RESULT_CANCELED, replyIntent);
        finish();
    }
}