package com.tm.elearningtm;
import java.util.ArrayList;
import java.util.List;

public class Profesor extends User {
    private String gradDidactic;
    private String departament;
    private List<Curs> cursuriPredate;

    public Profesor(
            int id,
            String nume,
            String email,
            String passHash,
            String gradDidactic,
            String departament
    ) {
        super(id, nume, email, passHash);
        this.gradDidactic = gradDidactic;
        this.departament = departament;
        this.cursuriPredate = new ArrayList<Curs>();
    }

    public void adaugaCurs(Curs curs) {
        cursuriPredate.add(curs);
    }

    public void editeazaCurs(Curs curs) {

    }

    public void stergeCurs(Curs curs) {
        cursuriPredate.remove(curs);
    }

    public void adaugaMaterial(Curs curs) {

    }

    public void adaugaSubmisie(Curs curs) {

    }

    public void noteazaSubmisie(SubmisieStudent submisie){

    }
}
