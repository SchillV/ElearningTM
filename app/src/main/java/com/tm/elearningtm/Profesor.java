package com.tm.elearningtm;

import java.util.ArrayList;
import java.util.List;

public class Profesor extends User {
    private GradDidactic gradDidactic;
    private String departament;
    private List<Curs> cursuriPredate;

    public Profesor(
            int id,
            String nume,
            String email,
            String passHash,
            GradDidactic gradDidactic,
            String departament
    ) {
        super(id, nume, email, passHash);
        this.gradDidactic = gradDidactic;
        this.departament = departament;
        this.cursuriPredate = new ArrayList<>();
    }

    public void adaugaCurs(Curs curs) {
        cursuriPredate.add(curs);
        System.out.println("Cursul " + curs.getTitlu() + " a fost adăugat.");
    }

    public void editeazaCurs(Curs curs) {
        System.out.println("Editarea cursului: " + curs.getTitlu());
    }

    public void stergeCurs(Curs curs) {
        cursuriPredate.remove(curs);
        System.out.println("Cursul " + curs.getTitlu() + " a fost șters.");
    }

    public void adaugaMaterial(Curs curs) {
        System.out.println("Material adăugat la cursul: " + curs.getTitlu());
    }

    public void adaugaSubmisie(Curs curs) {
        System.out.println("Submisie adăugată la cursul: " + curs.getTitlu());
    }

    public void noteazaSubmisie(SubmisieStudent submisie) {
        System.out.println("Submisia studentului " + submisie.getStudent().getNume() + " a fost notată.");
    }

    public GradDidactic getGradDidactic() {
        return gradDidactic;
    }

    public void setGradDidactic(GradDidactic gradDidactic) {
        this.gradDidactic = gradDidactic;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public List<Curs> getCursuriPredate() {
        return cursuriPredate;
    }
}
