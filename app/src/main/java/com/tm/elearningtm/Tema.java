package com.tm.elearningtm;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Tema extends Postare {
    private LocalDateTime deadline;
    private final List<SubmisieStudent> submisiiStudenti;

    public Tema(int id, String titlu, LocalDateTime deadline) {
        super(id, titlu);
        this.deadline = deadline;
        submisiiStudenti = new ArrayList<>();
    }

    public void adaugaSubmisie(SubmisieStudent submisie) {
        submisiiStudenti.add(submisie);
        System.out.println("Submisie adăugată pentru tema \"" + getTitlu() + "\".");
    }

    // Getters
    public LocalDateTime getDeadline() {
        return deadline;
    }

    public List<SubmisieStudent> getSubmisiiStudenti() {
        return submisiiStudenti;
    }

    // Setter
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
