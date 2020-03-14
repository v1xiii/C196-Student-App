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

import wgu.lschol1.c196.NoteDetails;
import wgu.lschol1.c196.R;
import wgu.lschol1.c196.database.NoteEntity;

import static wgu.lschol1.c196.Notes.NEW_NOTE_ACTIVITY_REQUEST_CODE;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private final Context context;
    private final LayoutInflater mInflater;
    private List<NoteEntity> mNotes;
    private Activity activity;

    public NotesAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.activity = (Activity) context;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteName;

        private NoteViewHolder(View itemView) {
            super(itemView);
            noteName = itemView.findViewById(R.id.item_title);
        }
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        if (mNotes != null) {
            NoteEntity note = mNotes.get(position);
            holder.noteName.setText(note.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.e("Test","Note clicked : "+position);

                    Intent intent = new Intent(context, NoteDetails.class);

                    intent.putExtra("noteId", note.getId());
                    intent.putExtra("noteName", note.getName());
                    intent.putExtra("noteGoalDate", note.getBodyText());
                    intent.putExtra("noteEntity", note);
                    System.out.println(note.getName());
                    activity.startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.noteName.setText("No Note");
        }
    }

    public void setNotes(List<NoteEntity> notes){
        mNotes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }
}