package com.tm.elearningtm;

public abstract class User {
    private int id;
    private String nume;
    private String email;
    private String passHash;

    public User(
            int id,
            String nume,
            String email,
            String passHash
    ) {
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.passHash = passHash;
    }

    public void login() {

    }

    public void logout() {

    }
}
