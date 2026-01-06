package com.tm.elearningtm.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tm.elearningtm.database.AppData;
import com.tm.elearningtm.fragments.CourseAssignments;
import com.tm.elearningtm.fragments.CourseGrades;
import com.tm.elearningtm.fragments.CourseMaterials;
import com.tm.elearningtm.fragments.CourseOverview;
import com.tm.elearningtm.fragments.CourseStudents;

public class CoursePagerAdapter extends FragmentStateAdapter {

    private final int courseId;
    private final boolean isStudent;

    public CoursePagerAdapter(@NonNull FragmentActivity fragmentActivity, int courseId) {
        super(fragmentActivity);
        this.courseId = courseId;
        this.isStudent = AppData.isStudent();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Common tabs for everyone
        switch (position) {
            case 0: // Overview
                return CourseOverview.newInstance(courseId);
            case 1: // Materials
                return CourseMaterials.newInstance();
            case 2: // Assignments
                return CourseAssignments.newInstance(courseId);
            case 3: // Grades (student) or Students (professor/admin)
                if (isStudent) {
                    return CourseGrades.newInstance();
                } else {
                    return CourseStudents.newInstance();
                }
            default:
                return CourseOverview.newInstance(courseId);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public String getTabTitle(int position) {
        switch (position) {
            case 0:
                return "Overview";
            case 1:
                return "Materials";
            case 2:
                return "Assignments";
            case 3:
                return isStudent ? "Grades" : "Students";
            default:
                return "";
        }
    }
}