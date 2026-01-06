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
import com.tm.elearningtm.activities.CourseDetail;
import com.tm.elearningtm.classes.Curs;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private List<Curs> courses;
    private final OnCourseListener onCourseListener;

    public CourseAdapter(List<Curs> courses, OnCourseListener onCourseListener) {
        this.courses = new ArrayList<>(courses);
        this.onCourseListener = onCourseListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);
        return new ViewHolder(view, onCourseListener);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateCourses(List<Curs> newCourses) {
        this.courses.clear();
        this.courses.addAll(newCourses);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Curs course = courses.get(position);
        holder.title.setText(course.getTitlu());
        holder.description.setText(course.getDescriere());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView title;
        public final TextView description;
        OnCourseListener onCourseListener;

        public ViewHolder(View view, OnCourseListener onCourseListener) {
            super(view);
            title = view.findViewById(R.id.text_course_title);
            description = view.findViewById(R.id.text_course_description);
            this.onCourseListener = onCourseListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCourseListener.onCourseClick(courses.get(getAdapterPosition()));
        }
    }

    public interface OnCourseListener {
        void onCourseClick(Curs curs);
    }
}