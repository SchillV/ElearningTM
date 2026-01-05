package com.tm.elearningtm.data;

import com.tm.elearningtm.classes.Catalog;
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.classes.UserRole;

public class AppData {

    //TODO: read the next id from a database
    private static int nextCursID = 1;
    private static int nextPostID = 1;
    private static int nextSubID = 1;
    private static int nextUserID = 1;
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

    public static int generateUserID() {
        return nextUserID++;
    }

    public static int generateCursID() {
        return nextCursID++;
    }

    public static int generateSubID() {
        return nextSubID++;
    }

    public static int generatePostID() {
        return nextPostID++;
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
