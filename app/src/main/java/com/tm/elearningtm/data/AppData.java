package com.tm.elearningtm.data;

import com.tm.elearningtm.classes.Catalog;
import com.tm.elearningtm.classes.User;

public class AppData {

    private static final Catalog catalog = new Catalog();
    private static User utilizatorCurent;

    public static Catalog getCatalog() {
        return catalog;
    }

    public static User getUtilizatorCurent() {
        return utilizatorCurent;
    }

    public static void setUtilizatorCurent(User user) {
        utilizatorCurent = user;
    }

    public static void logout() {
        utilizatorCurent = null;
    }
}
