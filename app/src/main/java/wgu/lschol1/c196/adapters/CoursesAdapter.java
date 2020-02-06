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

import wgu.lschol1.c196.CourseDetails;
import wgu.lschol1.c196.R;
import wgu.lschol1.c196.database.CourseEntity;

import static wgu.lschol1.c196.Courses.NEW_COURSE_ACTIVITY_REQUEST_CODE;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder> {

    private final Context context;
    private final LayoutInflater mInflater;
    private List<CourseEntity> mCourses;
    private Activity activity;

    public CoursesAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.activity = (Activity) context;
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseTitle;
        private final TextView courseStart;
        private final TextView courseEnd;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseTitle = itemView.findViewById(R.id.item_title);
            courseStart = itemView.findViewById(R.id.item_start);
            courseEnd = itemView.findViewById(R.id.item_end);
        }
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        if (mCourses != null) {
            CourseEntity course = mCourses.get(position);
            holder.courseTitle.setText(course.getTitle());
            holder.courseStart.setText(course.getStart());
            holder.courseEnd.setText(course.getEnd());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Test","Course clicked : "+position);

                    Intent intent = new Intent(context, CourseDetails.class);

                    intent.putExtra("courseId", course.getId());
                    intent.putExtra("courseName", course.getTitle());
                    intent.putExtra("courseStart", course.getStart());
                    intent.putExtra("courseEnd", course.getEnd());
                    intent.putExtra("courseEntity", course);

                    activity.startActivityForResult(intent, NEW_COURSE_ACTIVITY_REQUEST_CODE);
                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.courseTitle.setText("No Course");
        }
    }

    public void setCourses(List<CourseEntity> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }
}