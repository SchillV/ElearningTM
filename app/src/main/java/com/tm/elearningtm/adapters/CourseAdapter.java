package com.tm.elearningtm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.Curs;

import java.util.List;
import java.util.function.Consumer;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private final List<Curs> cursuri;
    private final Consumer<Curs> onClick;

    public CourseAdapter(List<Curs> cursuri, Consumer<Curs> onClick) {
        this.cursuri = cursuri;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemcourse, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Curs curs = cursuri.get(position);
        holder.title.setText(curs.getTitlu());
        holder.itemView.setOnClickListener(v -> onClick.accept(curs));
    }

    @Override
    public int getItemCount() {
        return cursuri.size();
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        CourseViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_course_title);
        }
    }
}
