package com.tm.elearningtm.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nume;
    private String email;
    private String passHash;

    @ColumnInfo(name = "user_role")
    private String role;

    // Student Fields (will be null for Professors)
    @ColumnInfo(name = "numar_matricol")
    private Integer numarMatricol; // Integer (not int) so it can be null

    @ColumnInfo(name = "an_studiu")
    private Integer anStudiu;

    private String grupa;

    // Profesor fields (will be null for Students)
    @ColumnInfo(name = "grad_didactic")
    private String gradDidactic;

    private String departament;

    public User() {
    }

    // Convenience constructor for Students
    public static User createStudent(int id, String nume, String email, String passHash,
                                     int numarMatricol, int anStudiu, String grupa) {
        User user = new User();
        user.id = id;
        user.nume = nume;
        user.email = email;
        user.passHash = passHash;
        user.role = "STUDENT";
        user.numarMatricol = numarMatricol;
        user.anStudiu = anStudiu;
        user.grupa = grupa;
        return user;
    }

    // Convenience constructor for Professors
    public static User createProfesor(int id, String nume, String email, String passHash,
                                      String gradDidactic, String departament) {
        User user = new User();
        user.id = id;
        user.nume = nume;
        user.email = email;
        user.passHash = passHash;
        user.role = "PROFESOR";
        user.gradDidactic = gradDidactic;
        user.departament = departament;
        return user;
    }

    // Helper methods
    public boolean isStudent() {
        return "STUDENT".equals(role);
    }

    public boolean isProfesor() {
        return "PROFESOR".equals(role);
    }

    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassHash() { return passHash; }
    public void setPassHash(String passHash) { this.passHash = passHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Student fields
    public Integer getNumarMatricol() { return numarMatricol; }
    public void setNumarMatricol(Integer numarMatricol) { this.numarMatricol = numarMatricol; }

    public Integer getAnStudiu() { return anStudiu; }
    public void setAnStudiu(Integer anStudiu) { this.anStudiu = anStudiu; }

    public String getGrupa() { return grupa; }
    public void setGrupa(String grupa) { this.grupa = grupa; }

    // Profesor fields
    public String getGradDidactic() { return gradDidactic; }
    public void setGradDidactic(String gradDidactic) { this.gradDidactic = gradDidactic; }

    public String getDepartament() { return departament; }
    public void setDepartament(String departament) { this.departament = departament; }
}