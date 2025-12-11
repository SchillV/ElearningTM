package com.tm.elearningtm;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Tema extends Postare {
    private LocalDateTime deadline;
    private List<SubmisieStudent> submisiiStudenti;

    public Tema(int id, String titlu, LocalDateTime deadline) {
        super(id, titlu);
        this.deadline = deadline;
        submisiiStudenti = new ArrayList<SubmisieStudent>();
    }
}
