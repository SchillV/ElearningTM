package com.tm.elearningtm;

public abstract class User {
    private final int id;
    private String nume;
    private String email;
    private String passHash;

    public User(int id, String nume, String email, String passHash) {
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.passHash = passHash;
    }

    public void login() {
        // de adaugat cod relevant la implementarea sesiunilor
        System.out.println(nume + " s-a autentificat.");
    }

    public void logout() {
        // de adaugat cod relevant la implementarea sesiunilor
        System.out.println(nume + " s-a delogat.");
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getEmail() {
        return email;
    }

    public String getPassHash() {
        return passHash;
    }

    // Setters
    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }
}
