package com.tm.elearningtm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Catalog {

    private final List<Curs> cursuri;
    private final List<User> utilizatori;
    private final List<MaterialCurs> materiale;
    private final List<SubmisieStudent> submisii;

    public Catalog() {
        this.cursuri = new ArrayList<>();
        this.utilizatori = new ArrayList<>();
        this.materiale = new ArrayList<>();
        this.submisii = new ArrayList<>();
    }

    public void adaugaCurs(Curs curs) {
        cursuri.add(curs);
        System.out.println("Curs \"" + curs.getTitlu() + "\" adăugat în catalog.");
    }

    public void stergeCurs(int idCurs) {
        Curs gasit = getCursById(idCurs);

        if (gasit == null) {
            System.out.println("Nu s-a găsit cursul cu ID-ul: " + idCurs);
            return;
        }

        cursuri.remove(gasit);
        System.out.println("Cursul \"" + gasit.getTitlu() + "\" a fost șters.");
    }

    public List<Curs> filtreazaDupaCategorie(String categorie) {
        return cursuri.stream()
                .filter(c -> c.getCategorie().equalsIgnoreCase(categorie))
                .collect(Collectors.toList());
    }

    public List<Curs> filtreazaDupaProfesor(Profesor profesor) {
        return cursuri.stream()
                .filter(c -> c.getProfesor().equals(profesor))
                .collect(Collectors.toList());
    }

    public Curs getCursById(int id) {
        for (Curs c : cursuri) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public void adaugaUtilizator(User user) {
        utilizatori.add(user);
        System.out.println("Utilizator \"" + user.getNume() + "\" adăugat în catalog.");
    }

    public User autentifica(String email, String parola) {
        for (User u : utilizatori) {
            if (u.getEmail().equalsIgnoreCase(email)) {

                if (u.getPassHash().equals(parola)) {
                    System.out.println("Autentificare reușită pentru " + u.getNume());
                    return u;
                }

                System.out.println("Parolă incorectă pentru utilizatorul " + u.getNume());
                return null;
            }
        }

        System.out.println("Nu există un utilizator cu email-ul: " + email);
        return null;
    }

    public void adaugaMaterial(MaterialCurs material) {
        materiale.add(material);
    }

    public void adaugaSubmisie(SubmisieStudent submisie) {
        submisii.add(submisie);
    }

    public List<MaterialCurs> getMateriale() {
        return materiale;
    }

    public List<SubmisieStudent> getSubmisii() {
        return submisii;
    }

    public List<Curs> getCursuri() {
        return cursuri;
    }

    public List<User> getUtilizatori() {
        return utilizatori;
    }
}