package com.tm.elearningtm.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tm.elearningtm.R;
import com.tm.elearningtm.models.CatalogueGrade;

import java.util.List;

public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.ViewHolder> {

    private final List<CatalogueGrade> grades;

    public CatalogueAdapter(List<CatalogueGrade> grades) {
        this.grades = grades;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_catalogue_grade, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CatalogueGrade grade = grades.get(position);
        holder.studentName.setText(grade.getStudentName());
        holder.courseName.setText("Course: " + grade.getCourseName());
        holder.assignmentName.setText("Assignment: " + grade.getAssignmentName());
        holder.grade.setText(String.valueOf(grade.getGrade()));
    }

    @Override
    public int getItemCount() {
        return grades.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView studentName;
        public final TextView courseName;
        public final TextView assignmentName;
        public final TextView grade;

        public ViewHolder(View view) {
            super(view);
            studentName = view.findViewById(R.id.text_student_name);
            courseName = view.findViewById(R.id.text_course_name);
            assignmentName = view.findViewById(R.id.text_assignment_name);
            grade = view.findViewById(R.id.text_grade);
        }
    }
}