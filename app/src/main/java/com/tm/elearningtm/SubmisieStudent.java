package com.tm.elearningtm;

import java.time.LocalDateTime;

public class SubmisieStudent {
    private Student student;
    private Tema tema;
    private float nota;
    private String feedback;
    private LocalDateTime data;

    public SubmisieStudent(
            Student student,
            Tema tema
    ) {
        this.student = student;
        this.tema = tema;
        nota = -1;
        feedback = "";
        data = LocalDateTime.now();
    }
}
