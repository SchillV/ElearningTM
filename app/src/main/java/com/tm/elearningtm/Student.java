package com.tm.elearningtm;
import java.util.ArrayList;
import java.util.List;

import kotlin.collections.EmptyList;

public class Student extends User {

    private int numarMatricol;
    private int anStudiu;
    private String grupa;
    private List<Curs> cursuriInscrise;
    private List<Curs> istoricCursuri;

    public Student (
            int id,
            String nume,
            String email,
            String passHash,
            int numarMatricol,
            int anStudiu,
            String grupa
    ) {
        super(id, nume, email, passHash);
        this.numarMatricol = numarMatricol;
        this.anStudiu = anStudiu;
        this.grupa = grupa;
        this.cursuriInscrise = new ArrayList<Curs>();
        this.istoricCursuri = new ArrayList<Curs>();
    }

    public void inscrieLaCurs (Curs curs) {
        cursuriInscrise.add(curs);
        istoricCursuri.add(curs);
    }

    public List<Curs> afiseazaCursuriInscrise() {
        return cursuriInscrise;
    }

    public List<Curs> afiseazaIstoricCursuri() {
        return istoricCursuri;
    }
}
