package com.tm.elearningtm;

import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private List<Curs> cursuri;
    private List<User> utilizatori;
    private List<MaterialCurs> materiale;
    private List<SubmisieStudent> submisii;

    public Catalog() {
        this.cursuri = new ArrayList<>();
        this.utilizatori = new ArrayList<>();
    }

    public void adaugaCurs(Curs curs) {

    }
    public void stergeCurs(String idCurs) {

    }
    public List<Curs> filtreazaDupaCategorie(String categorie) {
        return cursuri;
    }
    public List<Curs> filtreazaDupaProfesor(Profesor p) {
        return cursuri;
    }

    public void adaugaUtilizator(User user) {

    }
    public User autentifica(String email, String parola) {
        return utilizatori.get(0);
    }

    public Curs getCursById(String id) {
        return cursuri.get(0);
    }
}
