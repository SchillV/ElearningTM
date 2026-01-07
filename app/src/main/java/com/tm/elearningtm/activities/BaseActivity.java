package com.tm.elearningtm.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.tm.elearningtm.R;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppData;

@SuppressWarnings("deprecation")
public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;

    @IdRes
    private int currentNavId;

    protected void setupNavDrawer(@IdRes int navId) {
        this.currentNavId = navId;
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout != null) {
            navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            updateNavHeader();
            updateMenuVisibility();
        }
    }

    protected void setupToolbarWithBackButton() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (navigationView != null && currentNavId != 0) {
            navigationView.setCheckedItem(currentNavId);
        }
    }

    protected void updateNavHeader() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {
            View headerView = navigationView.getHeaderView(0);
            TextView navHeaderName = headerView.findViewById(R.id.text_nav_header_name);
            TextView navHeaderEmail = headerView.findViewById(R.id.text_nav_header_email);
            ImageView navHeaderRole = headerView.findViewById(R.id.image_nav_header_role);

            User currentUser = AppData.getUtilizatorCurent();
            if (currentUser != null) {
                navHeaderName.setText(currentUser.getNume());
                navHeaderEmail.setText(currentUser.getEmail());
                if (currentUser.isStudent()) {
                    navHeaderRole.setImageResource(R.drawable.ic_student);
                } else if (currentUser.isProfesor()) {
                    navHeaderRole.setImageResource(R.drawable.ic_profesor);
                } else {
                    navHeaderRole.setImageResource(R.drawable.ic_admin);
                }
            }
        }
    }

    protected void updateMenuVisibility() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {
            Menu menu = navigationView.getMenu();
            boolean isTeacher = AppData.isProfesor();
            boolean isAdmin = AppData.isAdmin();

            MenuItem teacherGroup = menu.findItem(R.id.group_teacher_actions);
            MenuItem adminGroup = menu.findItem(R.id.group_admin_actions);

            if (teacherGroup != null) {
                teacherGroup.setVisible(isTeacher || isAdmin);
            }
            if (adminGroup != null) {
                adminGroup.setVisible(isAdmin);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == currentNavId) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

        Intent intent = null;

        if (id == R.id.nav_dashboard) {
            intent = new Intent(this, Dashboard.class);
        } else if (id == R.id.nav_catalogue) {
            intent = new Intent(this, CatalogueActivity.class);
        } else if (id == R.id.nav_profile) {
            intent = new Intent(this, ProfileActivity.class);
        } else if (id == R.id.nav_admin_dashboard) {
            intent = new Intent(this, AdminDashboard.class);
        } else if (id == R.id.nav_logout) {
            AppData.logout();
            intent = new Intent(this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }

        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
