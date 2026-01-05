package com.tm.elearningtm.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Catalog {

    private final List<Curs> cursuri;
    private final List<User> utilizatori;

    public Catalog() {
        this.cursuri = new ArrayList<>();
        this.utilizatori = new ArrayList<>();
    }

    public void adaugaCurs(Curs curs) {
        cursuri.add(curs);
    }

    public void stergeCurs(int idCurs) {
        Curs gasit = getCursById(idCurs);

        if (gasit == null) {
            return;
        }

        cursuri.remove(gasit);
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
    }

    public User autentifica(String email, String parola) {
        for (User u : utilizatori) {
            if (u.getEmail().equalsIgnoreCase(email)) {

                if (u.getPassHash().equals(parola)) {
                    return u;
                }
                return null;
            }
        }
        return null;
    }

    public List<Curs> getCursuri() {
        return cursuri;
    }

    public List<User> getUtilizatori() {
        return utilizatori;
    }
}