package com.tm.elearningtm.adapters;

import android.content.Intent;
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
import com.tm.elearningtm.activities.AddCourseActivity;
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.database.AppData;

import java.util.List;

public class CourseManagementAdapter extends RecyclerView.Adapter<CourseManagementAdapter.ViewHolder> {

    private final List<Curs> courses;

    public CourseManagementAdapter(List<Curs> courses) {
        this.courses = courses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course_management, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Curs course = courses.get(position);
        holder.title.setText(course.getTitlu());
        holder.category.setText(course.getCategorie());

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddCourseActivity.class);
            intent.putExtra("EDIT_COURSE_ID", course.getId());
            v.getContext().startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Course")
                    .setMessage("Are you sure you want to delete '" + course.getTitlu() + "'?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        AppData.getDatabase().cursDao().delete(course);
                        courses.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, courses.size());
                        Toast.makeText(v.getContext(), course.getTitlu() + " has been deleted.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView category;
        public final ImageButton editButton;
        public final ImageButton deleteButton;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.text_course_title);
            category = view.findViewById(R.id.text_course_category);
            editButton = view.findViewById(R.id.button_edit_course);
            deleteButton = view.findViewById(R.id.button_delete_course);
        }
    }
}