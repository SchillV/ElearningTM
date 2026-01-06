package com.tm.elearningtm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.CourseEnrollment;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

import java.util.List;

public class SelectableStudentAdapter extends RecyclerView.Adapter<SelectableStudentAdapter.ViewHolder> {

    private final List<User> students;
    private final int courseId;

    public SelectableStudentAdapter(List<User> students, int courseId) {
        this.students = students;
        this.courseId = courseId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student_selectable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User student = students.get(position);
        holder.name.setText(student.getNume());
        holder.email.setText(student.getEmail());

        // Check if student is already enrolled
        boolean isEnrolled = AppData.getDatabase().enrollmentDao().isStudentEnrolled(student.getId(), courseId);

        if (isEnrolled) {
            holder.addButton.setText("Enrolled");
            holder.addButton.setEnabled(false);
        } else {
            holder.addButton.setText("Add");
            holder.addButton.setEnabled(true);
            holder.addButton.setOnClickListener(v -> {
                CourseEnrollment newEnrollment = new CourseEnrollment(student.getId(), courseId);
                AppData.getDatabase().enrollmentDao().insert(newEnrollment);
                Toast.makeText(v.getContext(), student.getNume() + " has been enrolled.", Toast.LENGTH_SHORT).show();
                // Visually update the button
                holder.addButton.setText("Enrolled");
                holder.addButton.setEnabled(false);
            });
        }
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView email;
        public final Button addButton;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.text_student_name);
            email = view.findViewById(R.id.text_student_email);
            addButton = view.findViewById(R.id.button_add_student);
        }
    }
}