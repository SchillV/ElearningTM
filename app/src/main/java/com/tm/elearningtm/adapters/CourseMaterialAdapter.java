package com.tm.elearningtm.adapters;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.MaterialCurs;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class CourseMaterialAdapter extends RecyclerView.Adapter<CourseMaterialAdapter.ViewHolder> {

    private final List<MaterialCurs> materials;

    public CourseMaterialAdapter(List<MaterialCurs> materials) {
        this.materials = materials;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course_material, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MaterialCurs material = materials.get(position);
        holder.title.setText(material.getTitlu());
        holder.description.setText(material.getDescriere());

        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            holder.date.setText("Posted on: " + material.getDataCreare().format(formatter));
        }
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView description;
        public final TextView date;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.text_material_title);
            description = view.findViewById(R.id.text_material_description);
            date = view.findViewById(R.id.text_material_date);
        }
    }
}