package com.tm.elearningtm.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private List<User> students;
    private final boolean isTeacher;

    public StudentAdapter(List<User> students, boolean isTeacher) {
        this.students = new ArrayList<>(students);
        this.isTeacher = isTeacher;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateStudents(List<User> newStudents) {
        this.students.clear();
        this.students.addAll(newStudents);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User student = students.get(position);
        holder.name.setText(student.getNume());
        holder.email.setText(student.getEmail());

        if (isTeacher) {
            holder.removeButton.setVisibility(View.VISIBLE);
            holder.removeButton.setOnClickListener(v -> {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Remove Student")
                        .setMessage("Are you sure you want to remove " + student.getNume() + " from this course?")
                        .setPositiveButton("Remove", (dialog, which) -> {
                            // Just perform the database operation. LiveData will handle the UI update.
                            AppData.getDatabase().enrollmentDao().deleteEnrollment(student.getId(), AppData.getCursCurent().getId());
                            Toast.makeText(v.getContext(), student.getNume() + " has been removed.", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            });
        } else {
            holder.removeButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView email;
        public final ImageButton removeButton;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.text_student_name);
            email = view.findViewById(R.id.text_student_email);
            removeButton = view.findViewById(R.id.button_remove_student);
        }
    }
}