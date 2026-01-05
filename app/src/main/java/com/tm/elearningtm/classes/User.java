package com.tm.elearningtm.classes;

public abstract class User {
    private final int id;
    private String nume;
    private String email;
    private String passHash;
    private final UserRole role;

    public User(int id, String nume, String email, String passHash, UserRole role) {
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.passHash = passHash;
        this.role = role;
    }

    public void login() {
        System.out.println(nume + " s-a autentificat.");
    }

    public void logout() {
        System.out.println(nume + " s-a delogat.");
    }

    // Getters
    public int getId() { return id; }
    public String getNume() { return nume; }
    public String getEmail() { return email; }
    public String getPassHash() { return passHash; }
    public UserRole getRole() { return role; }

    // Setters
    public void setNume(String nume) { this.nume = nume; }
    public void setEmail(String email) { this.email = email; }
    public void setPassHash(String passHash) { this.passHash = passHash; }
}
