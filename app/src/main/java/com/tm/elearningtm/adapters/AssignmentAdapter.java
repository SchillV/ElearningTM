package com.tm.elearningtm.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tm.elearningtm.R;
import com.tm.elearningtm.activities.SubmitAssignment;
import com.tm.elearningtm.classes.SubmisieStudent;
import com.tm.elearningtm.classes.Tema;
import com.tm.elearningtm.database.AppData;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {

    private final List<Tema> assignments;

    public AssignmentAdapter(List<Tema> assignments) {
        this.assignments = new ArrayList<>(assignments);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_assignment, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tema assignment = assignments.get(position);
        holder.title.setText(assignment.getTitlu());
        holder.description.setText(assignment.getDescriere());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            holder.deadline.setText("Deadline: " + assignment.getDeadline().format(formatter));
        }

        // Determine assignment status
        SubmisieStudent submission = AppData.getDatabase().submisieDao()
                .getSubmisieByStudentAndTema(AppData.getUtilizatorCurent().getId(), assignment.getId());

        Context context = holder.itemView.getContext();
        if (submission != null) {
            if (submission.getNota() != null) {
                holder.status.setText("Graded");
                holder.status.setBackground(ContextCompat.getDrawable(context, R.drawable.status_background_graded));
            } else {
                holder.status.setText("Submitted");
                holder.status.setBackground(ContextCompat.getDrawable(context, R.drawable.status_background_submitted));
            }
        } else {
            holder.status.setText("Unsolved");
            holder.status.setBackground(ContextCompat.getDrawable(context, R.drawable.status_background_unsolved));
        }

        // Set click listener for the whole item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SubmitAssignment.class);
            intent.putExtra("ASSIGNMENT_ID", assignment.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView description;
        public final TextView deadline;
        public final TextView status;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.text_assignment_title);
            description = view.findViewById(R.id.text_assignment_description);
            deadline = view.findViewById(R.id.text_assignment_deadline);
            status = view.findViewById(R.id.text_assignment_status);
        }
    }
}