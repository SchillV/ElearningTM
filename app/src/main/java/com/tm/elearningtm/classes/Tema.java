package com.tm.elearningtm.classes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Tema extends Postare {
    private LocalDateTime deadline;
    private final List<SubmisieStudent> submisii;

    public Tema(String titlu, String descriere, LocalDateTime deadline) {
        // TODO: add a way to give unique id's
        super(0, titlu, descriere);
        this.deadline = deadline;
        submisii = new ArrayList<>();
    }

    public void adaugaSubmisie(SubmisieStudent submisie) {
        submisii.add(submisie);
    }

    // Getters
    public LocalDateTime getDeadline() {
        return deadline;
    }

    public List<SubmisieStudent> getSubmisiiStudenti() {
        return submisii;
    }

    // Setter
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
