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

import wgu.lschol1.c196.adapters.TermsAdapter;
import wgu.lschol1.c196.database.TermEntity;
import wgu.lschol1.c196.viewmodels.TermsViewModel;

public class Terms extends AppCompatActivity {

    private TermsViewModel mTermsViewModel;
    public static final int NEW_TERM_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_terms);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView termsRecyclerView = findViewById(R.id.terms_recyclerview);
        final TermsAdapter adapter = new TermsAdapter(this);
        termsRecyclerView.setAdapter(adapter);
        termsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTermsViewModel = new ViewModelProvider(this).get(TermsViewModel.class);

        mTermsViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(@Nullable final List<TermEntity> terms) {
                adapter.setTerms(terms);
            }
        });
    }

    public void openTermDetailsPage(View view) {
        startActivityForResult(new Intent(Terms.this, TermDetails.class), NEW_TERM_ACTIVITY_REQUEST_CODE);
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

        if (requestCode == NEW_TERM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int termId = Objects.requireNonNull(extras).getInt(TermDetails.TERM_ID,0);
            String termName = extras.getString(TermDetails.TERM_NAME);
            String termStart = extras.getString(TermDetails.TERM_START);
            String termEnd = extras.getString(TermDetails.TERM_END);

            TermEntity term = new TermEntity(termId, termName, termStart, termEnd);
            mTermsViewModel.insert(term);

        } else {
            Toast.makeText(getApplicationContext(),R.string.empty_not_saved,Toast.LENGTH_LONG).show();
        }
    }
}