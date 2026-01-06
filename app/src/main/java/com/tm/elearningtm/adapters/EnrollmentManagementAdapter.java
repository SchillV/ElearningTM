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
import com.tm.elearningtm.classes.CourseEnrollment;
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

import java.util.List;

public class EnrollmentManagementAdapter extends RecyclerView.Adapter<EnrollmentManagementAdapter.ViewHolder> {

    private final List<CourseEnrollment> enrollments;

    public EnrollmentManagementAdapter(List<CourseEnrollment> enrollments) {
        this.enrollments = enrollments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_enrollment_management, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseEnrollment enrollment = enrollments.get(position);
        User student = AppData.getDatabase().userDao().getUserById(enrollment.getStudentId());
        Curs course = AppData.getDatabase().cursDao().getCursById(enrollment.getCourseId());

        if (student != null) {
            holder.studentName.setText(student.getNume());
        }
        if (course != null) {
            holder.courseName.setText("Course: " + course.getTitlu());
        }

        holder.deleteButton.setOnClickListener(v -> {
            assert course != null;
            assert student != null;
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Enrollment")
                    .setMessage("Are you sure you want to unenroll " + student.getNume() + " from " + course.getTitlu() + "?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        AppData.getDatabase().enrollmentDao().delete(enrollment);
                        enrollments.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, enrollments.size());
                        Toast.makeText(v.getContext(), "Enrollment deleted.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return enrollments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView studentName;
        public final TextView courseName;
        public final ImageButton deleteButton;

        public ViewHolder(View view) {
            super(view);
            studentName = view.findViewById(R.id.text_student_name);
            courseName = view.findViewById(R.id.text_course_name);
            deleteButton = view.findViewById(R.id.button_delete_enrollment);
        }
    }
}