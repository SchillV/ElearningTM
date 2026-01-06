package com.tm.elearningtm.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tm.elearningtm.R;
import com.tm.elearningtm.activities.SubmissionDetail;
import com.tm.elearningtm.classes.SubmisieStudent;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class SubmissionAdapter extends RecyclerView.Adapter<SubmissionAdapter.ViewHolder> {

    private final List<SubmisieStudent> submissions;

    public SubmissionAdapter(List<SubmisieStudent> submissions) {
        this.submissions = submissions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_submission, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubmisieStudent submission = submissions.get(position);
        User student = AppData.getDatabase().userDao().getUserById(submission.getStudentId());

        if (student != null) {
            holder.studentName.setText(student.getNume());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            holder.submissionDate.setText("Submitted on: " + submission.getDataSubmisie().format(formatter));
        }

        holder.submissionContent.setText(submission.getContinut());

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
        public final TextView studentName;
        public final TextView submissionDate;
        public final TextView submissionContent;

        public ViewHolder(View view) {
            super(view);
            studentName = view.findViewById(R.id.text_student_name);
            submissionDate = view.findViewById(R.id.text_submission_date);
            submissionContent = view.findViewById(R.id.text_submission_content);
        }
    }
}
