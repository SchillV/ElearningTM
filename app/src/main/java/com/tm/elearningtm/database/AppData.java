package com.tm.elearningtm.database;

import android.content.Context;

import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppDatabase;

public class AppData {

    private static AppDatabase database;
    private static User utilizatorCurent;
    private static Curs cursCurent;

    public static void initialize(Context context) {
        if (database == null) {
            database = AppDatabase.getInstance(context);
        }
    }

    public static AppDatabase getDatabase() {
        if (database == null) {
            throw new IllegalStateException("AppData not initialized! Call AppData.initialize(context) first.");
        }
        return database;
    }

    public static User getUtilizatorCurent() {
        return utilizatorCurent;
    }

    public static void setUtilizatorCurent(User user) {
        utilizatorCurent = user;
    }

    public static boolean isStudent() {
        return utilizatorCurent != null && "STUDENT".equals(utilizatorCurent.getRole());
    }

    public static boolean isProfesor() {
        return utilizatorCurent != null && "PROFESOR".equals(utilizatorCurent.getRole());
    }

    public static boolean isAdmin() {
        return utilizatorCurent != null && "ADMIN".equals(utilizatorCurent.getRole());
    }

    public static Curs getCursCurent() {
        return cursCurent;
    }

    public static void setCursCurent(Curs curs) {
        cursCurent = curs;
    }

    public static void logout() {
        utilizatorCurent = null;
        cursCurent = null;
    }
}
