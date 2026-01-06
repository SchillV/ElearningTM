package com.tm.elearningtm.database;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.tm.elearningtm.classes.CourseEnrollment;
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.classes.MaterialCurs;
import com.tm.elearningtm.classes.SubmisieStudent;
import com.tm.elearningtm.classes.Tema;
import com.tm.elearningtm.classes.User;
import com.tm.elearningtm.database.AppDatabase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Seeds the database with test data for development.
 * Run this once when the app first starts.
 */
public class DatabaseSeeder {

    private static final String TAG = "DatabaseSeeder";
    private static final Executor executor = Executors.newSingleThreadExecutor();

    /**
     * Seeds the database with test data.
     * This runs asynchronously to avoid blocking the UI.
     *
     * @param context Application context
     */
    public static void seedDatabase(Context context) {
        executor.execute(() -> {
            AppDatabase db = AppData.getDatabase();

            // Check if already seeded
            if (db.userDao().getUserCount() > 0) {
                Log.d(TAG, "Database already contains data. Skipping seed.");
                return;
            }

            Log.d(TAG, "Seeding database with test data...");

            try {
                seedUsers(db);
                seedCourses(db);
                seedEnrollments(db);
                seedAssignments(db);
                seedMaterials(db);
                seedSubmissions(db);

                Log.d(TAG, "✅ Database seeded successfully!");
                Log.d(TAG, "Test Credentials:");
                Log.d(TAG, "  Professor: popescu@univ.ro / prof123");
                Log.d(TAG, "  Professor: ionescu@univ.ro / prof123");
                Log.d(TAG, "  Student: andrei@stud.univ.ro / student123");
                Log.d(TAG, "  Student: elena@stud.univ.ro / student123");
                Log.d(TAG, "  Student: mihai@stud.univ.ro / student123");

            } catch (Exception e) {
                Log.e(TAG, "Error seeding database", e);
            }
        });
    }

    /**
     * Create test users (professors and students)
     */
    private static void seedUsers(AppDatabase db) {
        // ========== PROFESSORS ==========
        User prof1 = User.createProfesor(
                0,
                "Ion Popescu",
                "popescu@univ.ro",
                "prof123",  // In production, hash this!
                "CONFERENTIAR",
                "Informatică"
        );
        long prof1Id = db.userDao().insert(prof1);

        User prof2 = User.createProfesor(
                0,
                "Maria Ionescu",
                "ionescu@univ.ro",
                "prof123",
                "LECTOR",
                "Matematică"
        );
        long prof2Id = db.userDao().insert(prof2);

        User prof3 = User.createProfesor(
                0,
                "Alexandru Gheorghiu",
                "gheorghiu@univ.ro",
                "prof123",
                "PROFESOR",
                "Informatică"
        );
        long prof3Id = db.userDao().insert(prof3);

        // ========== STUDENTS ==========
        User student1 = User.createStudent(
                0,
                "Andrei Georgescu",
                "andrei@stud.univ.ro",
                "student123",
                12345,
                3,
                "A1"
        );
        long student1Id = db.userDao().insert(student1);

        User student2 = User.createStudent(
                0,
                "Elena Popa",
                "elena@stud.univ.ro",
                "student123",
                12346,
                3,
                "A1"
        );
        long student2Id = db.userDao().insert(student2);

        User student3 = User.createStudent(
                0,
                "Mihai Dumitrescu",
                "mihai@stud.univ.ro",
                "student123",
                12347,
                3,
                "A2"
        );
        long student3Id = db.userDao().insert(student3);

        User student4 = User.createStudent(
                0,
                "Ana Marinescu",
                "ana@stud.univ.ro",
                "student123",
                12348,
                2,
                "B1"
        );
        db.userDao().insert(student4);

        User student5 = User.createStudent(
                0,
                "Cristian Vasile",
                "cristian@stud.univ.ro",
                "student123",
                12349,
                2,
                "B1"
        );
        db.userDao().insert(student5);

        Log.d(TAG, "✓ Created 3 professors and 5 students");
    }

    /**
     * Create test courses
     */
    private static void seedCourses(AppDatabase db) {
        // Get professors
        User prof1 = db.userDao().getUserByEmail("popescu@univ.ro");
        User prof2 = db.userDao().getUserByEmail("ionescu@univ.ro");
        User prof3 = db.userDao().getUserByEmail("gheorghiu@univ.ro");

        // Course 1: Tehnologii Mobile
        Curs curs1 = new Curs(
                0,
                "Tehnologii Mobile",
                "Dezvoltarea aplicațiilor mobile pentru Android și iOS. " +
                        "Cursul acoperă limbaje de programare, framework-uri și best practices.",
                "Informatică",
                prof1.getId()
        );
        long curs1Id = db.cursDao().insert(curs1);

        // Course 2: Baze de Date
        Curs curs2 = new Curs(
                0,
                "Baze de Date",
                "Proiectare și implementare de baze de date relaționale și NoSQL. " +
                        "SQL, normalizare, indexare, și optimizare de query-uri.",
                "Informatică",
                prof1.getId()
        );
        long curs2Id = db.cursDao().insert(curs2);

        // Course 3: Analiză Matematică
        Curs curs3 = new Curs(
                0,
                "Analiză Matematică",
                "Calcul diferențial și integral. Serii, șiruri, funcții de mai multe variabile.",
                "Matematică",
                prof2.getId()
        );
        long curs3Id = db.cursDao().insert(curs3);

        // Course 4: Algoritmi Fundamentali
        Curs curs4 = new Curs(
                0,
                "Algoritmi Fundamentali",
                "Algoritmi de sortare, căutare, grafuri. Analiza complexității. " +
                        "Programare dinamică și metoda Greedy.",
                "Informatică",
                prof3.getId()
        );
        long curs4Id = db.cursDao().insert(curs4);

        // Course 5: Structuri de Date
        Curs curs5 = new Curs(
                0,
                "Structuri de Date",
                "Liste, stive, cozi, arbori, hash tables. " +
                        "Implementare și utilizare în rezolvarea problemelor.",
                "Informatică",
                prof3.getId()
        );
        db.cursDao().insert(curs5);

        Log.d(TAG, "✓ Created 5 courses");
    }

    /**
     * Enroll students in courses
     */
    private static void seedEnrollments(AppDatabase db) {
        User andrei = db.userDao().getUserByEmail("andrei@stud.univ.ro");
        User elena = db.userDao().getUserByEmail("elena@stud.univ.ro");
        User mihai = db.userDao().getUserByEmail("mihai@stud.univ.ro");
        User ana = db.userDao().getUserByEmail("ana@stud.univ.ro");
        User cristian = db.userDao().getUserByEmail("cristian@stud.univ.ro");

        Curs tm = db.cursDao().searchCoursesByTitle("Tehnologii Mobile").get(0);
        Curs bd = db.cursDao().searchCoursesByTitle("Baze de Date").get(0);
        Curs am = db.cursDao().searchCoursesByTitle("Analiză").get(0);
        Curs af = db.cursDao().searchCoursesByTitle("Algoritmi").get(0);
        Curs sd = db.cursDao().searchCoursesByTitle("Structuri").get(0);

        // Andrei: enrolled in TM, BD, AF
        db.enrollmentDao().insert(new CourseEnrollment(andrei.getId(), tm.getId()));
        db.enrollmentDao().insert(new CourseEnrollment(andrei.getId(), bd.getId()));
        db.enrollmentDao().insert(new CourseEnrollment(andrei.getId(), af.getId()));

        // Elena: enrolled in TM, BD, AM
        db.enrollmentDao().insert(new CourseEnrollment(elena.getId(), tm.getId()));
        db.enrollmentDao().insert(new CourseEnrollment(elena.getId(), bd.getId()));
        db.enrollmentDao().insert(new CourseEnrollment(elena.getId(), am.getId()));

        // Mihai: enrolled in TM, AF, SD
        db.enrollmentDao().insert(new CourseEnrollment(mihai.getId(), tm.getId()));
        db.enrollmentDao().insert(new CourseEnrollment(mihai.getId(), af.getId()));
        db.enrollmentDao().insert(new CourseEnrollment(mihai.getId(), sd.getId()));

        // Ana: enrolled in AF, SD, AM
        db.enrollmentDao().insert(new CourseEnrollment(ana.getId(), af.getId()));
        db.enrollmentDao().insert(new CourseEnrollment(ana.getId(), sd.getId()));
        db.enrollmentDao().insert(new CourseEnrollment(ana.getId(), am.getId()));

        // Cristian: enrolled in BD, SD
        db.enrollmentDao().insert(new CourseEnrollment(cristian.getId(), bd.getId()));
        db.enrollmentDao().insert(new CourseEnrollment(cristian.getId(), sd.getId()));

        Log.d(TAG, "✓ Created course enrollments");
    }

    /**
     * Create assignments (teme) for courses
     */
    private static void seedAssignments(AppDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Log.d(TAG, "⚠ Skipping assignments - requires API 26+");
            return;
        }

        List<Curs> cursuri = db.cursDao().getAllCourses();

        for (Curs curs : cursuri) {
            if (curs.getTitlu().equals("Tehnologii Mobile")) {
                // TM assignments
                Tema tema1 = new Tema(
                        "Proiect Android - Calculator",
                        "Creați o aplicație calculator cu operații de bază (+, -, *, /). " +
                                "Trebuie să aibă un UI plăcut și să gestioneze corect erorile.",
                        LocalDateTime.now().plusDays(14)
                );
                tema1.setCursId(curs.getId());
                db.temaDao().insert(tema1);

                Tema tema2 = new Tema(
                        "RecyclerView și Adapters",
                        "Implementați o listă de produse folosind RecyclerView. " +
                                "Fiecare item trebuie să afișeze imagine, nume, preț.",
                        LocalDateTime.now().plusDays(21)
                );
                tema2.setCursId(curs.getId());
                db.temaDao().insert(tema2);

                Tema tema3 = new Tema(
                        "Integrare API REST",
                        "Conectați aplicația la un API public (ex: OpenWeather) și afișați date.",
                        LocalDateTime.now().plusDays(28)
                );
                tema3.setCursId(curs.getId());
                db.temaDao().insert(tema3);

            } else if (curs.getTitlu().equals("Baze de Date")) {
                // BD assignments
                Tema tema1 = new Tema(
                        "Design Bază de Date - Bibliotecă",
                        "Proiectați schema unei baze de date pentru o bibliotecă. " +
                                "Includeți diagrama ER, normalizare, și justificări.",
                        LocalDateTime.now().plusDays(10)
                );
                tema1.setCursId(curs.getId());
                db.temaDao().insert(tema1);

                Tema tema2 = new Tema(
                        "Query-uri SQL Avansate",
                        "Rezolvați 15 exerciții SQL care acoperă JOIN-uri, subquery-uri, " +
                                "funcții de agregare, și window functions.",
                        LocalDateTime.now().plusDays(17)
                );
                tema2.setCursId(curs.getId());
                db.temaDao().insert(tema2);

            } else if (curs.getTitlu().equals("Algoritmi Fundamentali")) {
                // AF assignments
                Tema tema1 = new Tema(
                        "Implementare Sortări",
                        "Implementați QuickSort, MergeSort, și HeapSort. " +
                                "Comparați performanța pe diferite seturi de date.",
                        LocalDateTime.now().plusDays(7)
                );
                tema1.setCursId(curs.getId());
                db.temaDao().insert(tema1);

                Tema tema2 = new Tema(
                        "Probleme de Grafuri",
                        "Rezolvați 5 probleme de grafuri: BFS, DFS, Dijkstra, " +
                                "componente conexe, ciclu eulerian.",
                        LocalDateTime.now().plusDays(14)
                );
                tema2.setCursId(curs.getId());
                db.temaDao().insert(tema2);
            }
        }

        Log.d(TAG, "✓ Created assignments for courses");
    }

    /**
     * Create course materials
     */
    private static void seedMaterials(AppDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Log.d(TAG, "⚠ Skipping materials - requires API 26+");
            return;
        }

        List<Curs> cursuri = db.cursDao().getAllCourses();

        for (Curs curs : cursuri) {
            // Lecții
            MaterialCurs lectie1 = new MaterialCurs(
                    0,
                    "Introducere în " + curs.getTitlu(),
                    "Prezentare generală a cursului, obiective, și structura cursului.",
                    curs.getId(),
                    "LECTIE",
                    LocalDateTime.now().minusDays(30)
            );
            db.materialDao().insert(lectie1);

            MaterialCurs lectie2 = new MaterialCurs(
                    0,
                    "Capitolul 1 - Concepte Fundamentale",
                    "Noțiunile de bază necesare pentru înțelegerea materiei.",
                    curs.getId(),
                    "LECTIE",
                    LocalDateTime.now().minusDays(25)
            );
            db.materialDao().insert(lectie2);

            // Laborator
            MaterialCurs lab1 = new MaterialCurs(
                    0,
                    "Laborator 1 - Setup și Hello World",
                    "Configurarea mediului de dezvoltare și primul program.",
                    curs.getId(),
                    "LABORATOR",
                    LocalDateTime.now().minusDays(23)
            );
            db.materialDao().insert(lab1);

            // Anunț
            MaterialCurs anunt = new MaterialCurs(
                    0,
                    "Important: Deadline Proiect",
                    "Nu uitați că deadline-ul pentru proiect este peste 2 săptămâni!",
                    curs.getId(),
                    "ANUNT",
                    LocalDateTime.now().minusDays(5)
            );
            db.materialDao().insert(anunt);

            // Resursă
            MaterialCurs resursa = new MaterialCurs(
                    0,
                    "Resurse Adiționale",
                    "Linkuri către tutoriale, documentație oficială, și articole utile.",
                    curs.getId(),
                    "RESURSA",
                    LocalDateTime.now().minusDays(20)
            );
            db.materialDao().insert(resursa);
        }

        Log.d(TAG, "✓ Created materials for courses");
    }

    /**
     * Create some test submissions
     */
    private static void seedSubmissions(AppDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Log.d(TAG, "⚠ Skipping submissions - requires API 26+");
            return;
        }

        User andrei = db.userDao().getUserByEmail("andrei@stud.univ.ro");
        User elena = db.userDao().getUserByEmail("elena@stud.univ.ro");
        User mihai = db.userDao().getUserByEmail("mihai@stud.univ.ro");

        // Get some assignments
        List<Tema> teme = db.temaDao().getAllTeme();

        if (!teme.isEmpty() && teme.size() >= 3) {
            // Andrei submits to first assignment
            SubmisieStudent sub1 = new SubmisieStudent(
                    0,
                    andrei.getId(),
                    teme.get(0).getId(),
                    "Am implementat calculatorul conform cerințelor. " +
                            "Am folosit ConstraintLayout pentru UI și am gestionat toate cazurile de eroare.",
                    LocalDateTime.now().minusDays(2),
                    9.5  // Already graded
            );
            db.submisieDao().insert(sub1);

            // Elena submits to first assignment
            SubmisieStudent sub2 = new SubmisieStudent(
                    0,
                    elena.getId(),
                    teme.get(0).getId(),
                    "Proiect finalizat. Aplicația funcționează corect și are un design modern.",
                    LocalDateTime.now().minusDays(1),
                    8.0  // Already graded
            );
            db.submisieDao().insert(sub2);

            // Mihai submits to second assignment (not graded yet)
            SubmisieStudent sub3 = new SubmisieStudent(
                    0,
                    mihai.getId(),
                    teme.get(1).getId(),
                    "RecyclerView implementat. Am adăugat și funcționalitate de click pe items.",
                    LocalDateTime.now().minusHours(5),
                    null  // Not graded yet
            );
            db.submisieDao().insert(sub3);

            Log.d(TAG, "✓ Created test submissions");
        }
    }
}