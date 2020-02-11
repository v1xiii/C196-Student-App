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

import wgu.lschol1.c196.adapters.MentorsAdapter;
import wgu.lschol1.c196.database.MentorEntity;
import wgu.lschol1.c196.viewmodels.MentorsViewModel;

public class Mentors extends AppCompatActivity {

    private MentorsViewModel mMentorsViewModel;
    public static final int NEW_MENTOR_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mentors);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView mentorsRecyclerView = findViewById(R.id.mentors_recyclerview);
        final MentorsAdapter adapter = new MentorsAdapter(this);
        mentorsRecyclerView.setAdapter(adapter);
        mentorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMentorsViewModel = new ViewModelProvider(this).get(MentorsViewModel.class);

        mMentorsViewModel.getAllMentors().observe(this, new Observer<List<MentorEntity>>() {
            @Override
            public void onChanged(@Nullable final List<MentorEntity> mentors) {
                adapter.setMentors(mentors);
            }
        });
    }

    public void openMentorDetailsPage(View view) {
        startActivityForResult(new Intent(Mentors.this, MentorDetails.class), NEW_MENTOR_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

        if (requestCode == NEW_MENTOR_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int mentorId = Objects.requireNonNull(extras).getInt(MentorDetails.MENTOR_ID,0);
            String mentorName = extras.getString(MentorDetails.MENTOR_NAME);
            String mentorPhone = extras.getString(MentorDetails.MENTOR_PHONE);
            String mentorEmail = extras.getString(MentorDetails.MENTOR_EMAIL);

            MentorEntity mentor = new MentorEntity(mentorId, mentorName, mentorPhone, mentorEmail);
            mMentorsViewModel.insert(mentor);

        } else {
            Toast.makeText(getApplicationContext(),R.string.empty_not_saved,Toast.LENGTH_LONG).show();
        }
    }
}