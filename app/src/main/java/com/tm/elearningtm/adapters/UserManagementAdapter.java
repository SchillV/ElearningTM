package com.tm.elearningtm.adapters;

import android.annotation.SuppressLint;
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
import com.tm.elearningtm.activities.admin.EditUserActivity;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

import java.util.List;

public class UserManagementAdapter extends RecyclerView.Adapter<UserManagementAdapter.ViewHolder> {

    private final List<User> users;

    public UserManagementAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_management, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.name.setText(user.getNume());
        holder.email.setText(user.getEmail());
        holder.role.setText("Role: " + user.getRole());

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditUserActivity.class);
            intent.putExtra("USER_ID", user.getId());
            v.getContext().startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> new AlertDialog.Builder(v.getContext())
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete " + user.getNume() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    AppData.getDatabase().userDao().delete(user);
                    users.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, users.size());
                    Toast.makeText(v.getContext(), user.getNume() + " has been deleted.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView email;
        public final TextView role;
        public final ImageButton editButton;
        public final ImageButton deleteButton;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.text_user_name);
            email = view.findViewById(R.id.text_user_email);
            role = view.findViewById(R.id.text_user_role);
            editButton = view.findViewById(R.id.button_edit_user);
            deleteButton = view.findViewById(R.id.button_delete_user);
        }
    }
}
