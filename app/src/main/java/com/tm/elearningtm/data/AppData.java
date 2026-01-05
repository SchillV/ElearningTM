package com.tm.elearningtm.data;

import com.tm.elearningtm.classes.Catalog;
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.classes.UserRole;

public class AppData {

    private static final Catalog catalog = new Catalog();
    private static User utilizatorCurent;
    private static Curs cursCurent;

    public static Catalog getCatalog() {
        return catalog;
    }

    public static User getUtilizatorCurent() {
        return utilizatorCurent;
    }

    public static boolean isStudent() {
        return utilizatorCurent.getRole() == UserRole.STUDENT;
    }

    public static boolean isProfesor() {
        return utilizatorCurent.getRole() == UserRole.PROFESOR;
    }

    public static Curs getCursCurent() {
        return cursCurent;
    }

    public static void setUtilizatorCurent(User usr) {
        utilizatorCurent = usr;
    }

    public static void setCursCurent(Curs curs) {
        cursCurent = curs;
    }

    public static void logout() {
        utilizatorCurent = null;
    }
}
