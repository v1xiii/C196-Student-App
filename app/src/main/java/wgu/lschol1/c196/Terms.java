package wgu.lschol1.c196;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import wgu.lschol1.c196.adapters.TermsAdapter;
import wgu.lschol1.c196.viewmodels.TermsViewModel;

public class Terms extends AppCompatActivity {

    private TermsViewModel mTermsViewModel;

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

        //mTermsViewModel = new ViewModelProvider(this).get(TermsViewModel.class); // cannot resolve constructor ViewModelProvider(wgu.lschol1.c196.Terms)
    }

    public void openTermDetailsPage(View view) {
        startActivity(new Intent(Terms.this, TermDetails.class));
    }
}