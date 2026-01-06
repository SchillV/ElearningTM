package com.tm.elearningtm.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tm.elearningtm.R;
import com.tm.elearningtm.activities.SubmissionDetail;
import com.tm.elearningtm.classes.SubmisieStudent;
import com.tm.elearningtm.classes.Tema;
import com.tm.elearningtm.database.AppData;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder> {

    private final List<SubmisieStudent> submissions;

    public GradeAdapter(List<SubmisieStudent> submissions) {
        this.submissions = submissions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grade, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubmisieStudent submission = submissions.get(position);
        Tema assignment = AppData.getDatabase().temaDao().getTemaById(submission.getTemaId());

        if (assignment != null) {
            holder.assignmentTitle.setText(assignment.getTitlu());
        }

        if (submission.getNota() != null) {
            holder.grade.setText("Grade: " + submission.getNota());
        } else {
            holder.grade.setText("Not graded yet");
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SubmissionDetail.class);
            intent.putExtra("SUBMISSION_ID", submission.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return submissions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView assignmentTitle;
        public final TextView grade;

        public ViewHolder(View view) {
            super(view);
            assignmentTitle = view.findViewById(R.id.text_assignment_title);
            grade = view.findViewById(R.id.text_grade);
        }
    }
}
