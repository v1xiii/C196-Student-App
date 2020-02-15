package wgu.lschol1.c196.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wgu.lschol1.c196.R;
import wgu.lschol1.c196.AssessmentDetails;
import wgu.lschol1.c196.database.AssessmentEntity;

import static wgu.lschol1.c196.Assessments.NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE;

public class AssessmentsAdapter extends RecyclerView.Adapter<AssessmentsAdapter.AssessmentViewHolder> {

    private final Context context;
    private final LayoutInflater mInflater;
    private List<AssessmentEntity> mAssessments;
    private Activity activity;

    public AssessmentsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.activity = (Activity) context;
    }

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentName;
        //private final TextView assessmentGoalDate;
        //private final TextView assessmentDueDate;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentName = itemView.findViewById(R.id.item_title);
            //assessmentGoalDate = itemView.findViewById(R.id.item_start);
            //assessmentDueDate = itemView.findViewById(R.id.item_end);
        }
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            AssessmentEntity assessment = mAssessments.get(position);
            holder.assessmentName.setText(assessment.getName());
            //holder.assessmentGoalDate.setText(assessment.getGoalDate());
            //holder.assessmentDueDate.setText(assessment.getDueDate());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Test","Assessment clicked : "+position);

                    Intent intent = new Intent(context, AssessmentDetails.class);

                    intent.putExtra("assessmentId", assessment.getId());
                    intent.putExtra("assessmentName", assessment.getName());
                    intent.putExtra("assessmentGoalDate", assessment.getGoalDate());
                    intent.putExtra("assessmentDueDate", assessment.getDueDate());
                    intent.putExtra("assessmentType", assessment.getDueDate());
                    intent.putExtra("assessmentCourse", assessment.getDueDate());
                    intent.putExtra("assessmentEntity", assessment);
                    System.out.println(assessment.getName());
                    activity.startActivityForResult(intent, NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE);
                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.assessmentName.setText("No Assessment");
        }
    }

    public void setAssessments(List<AssessmentEntity> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null)
            return mAssessments.size();
        else return 0;
    }
}