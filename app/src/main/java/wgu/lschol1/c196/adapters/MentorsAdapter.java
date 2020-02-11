package wgu.lschol1.c196.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wgu.lschol1.c196.R;
import wgu.lschol1.c196.MentorDetails;
import wgu.lschol1.c196.database.MentorEntity;

import static wgu.lschol1.c196.Mentors.NEW_MENTOR_ACTIVITY_REQUEST_CODE;

public class MentorsAdapter extends RecyclerView.Adapter<MentorsAdapter.MentorViewHolder> {

    private final Context context;
    private final LayoutInflater mInflater;
    private List<MentorEntity> mMentors;
    private Activity activity;

    public MentorsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.activity = (Activity) context;
    }

    class MentorViewHolder extends RecyclerView.ViewHolder {
        private final TextView mentorName;
        //private final TextView mentorPhone;
        //private final TextView mentorEmail;

        private MentorViewHolder(View itemView) {
            super(itemView);
            mentorName = itemView.findViewById(R.id.item_title);
            //mentorPhone = itemView.findViewById(R.id.item_start);
            //mentorEmail = itemView.findViewById(R.id.item_end);
        }
    }

    @NonNull
    @Override
    public MentorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new MentorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorViewHolder holder, int position) {
        if (mMentors != null) {
            MentorEntity mentor = mMentors.get(position); // this is good

            holder.mentorName.setText(mentor.getName()); // this is not
            //holder.mentorPhone.setText(mentor.getPhone());
            //holder.mentorEmail.setText(mentor.getEmail());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Test","Mentor clicked : "+position);

                    Intent intent = new Intent(context, MentorDetails.class);

                    intent.putExtra("mentorId", mentor.getId());
                    intent.putExtra("mentorName", mentor.getName());
                    intent.putExtra("mentorPhone", mentor.getPhone());
                    intent.putExtra("mentorEmail", mentor.getEmail());
                    intent.putExtra("mentorEntity", mentor);

                    activity.startActivityForResult(intent, NEW_MENTOR_ACTIVITY_REQUEST_CODE);
                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.mentorName.setText("No Mentor");
        }
    }

    public void setMentors(List<MentorEntity> mentors){
        mMentors = mentors;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mMentors != null)
            return mMentors.size();
        else return 0;
    }
}