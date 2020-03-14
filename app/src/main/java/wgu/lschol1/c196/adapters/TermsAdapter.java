package wgu.lschol1.c196.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wgu.lschol1.c196.R;
import wgu.lschol1.c196.TermDetails;
import wgu.lschol1.c196.database.TermEntity;

import static wgu.lschol1.c196.Terms.NEW_TERM_ACTIVITY_REQUEST_CODE;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.TermViewHolder> {

    private final Context context;
    private final LayoutInflater mInflater;
    private List<TermEntity> mTerms;
    private Activity activity;

    public TermsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.activity = (Activity) context;
    }

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termTitle;
        private final TextView termStart;
        private final TextView termEnd;

        private TermViewHolder(View itemView) {
            super(itemView);
            termTitle = itemView.findViewById(R.id.item_title);
            termStart = itemView.findViewById(R.id.item_start);
            termEnd = itemView.findViewById(R.id.item_end);
        }
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {
        if (mTerms != null) {
            TermEntity term = mTerms.get(position);
            holder.termTitle.setText(term.getTitle());
            holder.termStart.setText(term.getStart());
            holder.termEnd.setText(term.getEnd());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.e("Test","Term clicked : "+position);

                    Intent intent = new Intent(context, TermDetails.class);

                    intent.putExtra("termId", term.getId());
                    intent.putExtra("termName", term.getTitle());
                    intent.putExtra("termStart", term.getStart());
                    intent.putExtra("termEnd", term.getEnd());
                    intent.putExtra("termEntity", term);

                    activity.startActivityForResult(intent, NEW_TERM_ACTIVITY_REQUEST_CODE);
                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.termTitle.setText("No Term");
        }
    }

    public void setTerms(List<TermEntity> terms){
        mTerms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTerms != null)
            return mTerms.size();
        else return 0;
    }
}