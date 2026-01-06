package com.tm.elearningtm.activities.admin;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tm.elearningtm.R;
import com.tm.elearningtm.adapters.UserManagementAdapter;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class ManageUsersActivity extends AppCompatActivity {

    private UserManagementAdapter adapter;
    private List<User> allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Manage Users");

        allUsers = AppData.getDatabase().userDao().getAllUsers();

        RecyclerView recyclerView = findViewById(R.id.recycler_view_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserManagementAdapter(allUsers);
        recyclerView.setAdapter(adapter);

        setupSearch();
    }

    private void setupSearch() {
        SearchView searchView = findViewById(R.id.search_view_users);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String text) {
        List<User> filteredList = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getNume().toLowerCase().contains(text.toLowerCase()) ||
                    user.getEmail().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(user);
            }
        }
        adapter = new UserManagementAdapter(filteredList);
        ((RecyclerView) findViewById(R.id.recycler_view_users)).setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
