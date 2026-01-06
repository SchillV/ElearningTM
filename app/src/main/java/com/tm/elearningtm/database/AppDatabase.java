package com.tm.elearningtm.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.tm.elearningtm.classes.CourseEnrollment;
import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.classes.MaterialCurs;
import com.tm.elearningtm.classes.SubmisieStudent;
import com.tm.elearningtm.classes.Tema;
import com.tm.elearningtm.classes.User;

@Database(
        entities = {
                User.class,
                Curs.class,
                Tema.class,
                MaterialCurs.class,
                SubmisieStudent.class,
                CourseEnrollment.class
        },
        version = 1,
        exportSchema = false
)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    private static final String DATABASE_NAME = "elearning_database.db";

    // DAO accessors
    public abstract UserDao userDao();
    public abstract CursDao cursDao();
    public abstract TemaDao temaDao();
    public abstract MaterialDao materialDao();
    public abstract SubmisieDao submisieDao();
    public abstract EnrollmentDao enrollmentDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        DATABASE_NAME
                )
                .allowMainThreadQueries()  // Remove in production!
                .fallbackToDestructiveMigration()
                .addCallback(DATABASE_CALLBACK)
                .build();
    }

    private static final RoomDatabase.Callback DATABASE_CALLBACK = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@androidx.annotation.NonNull androidx.sqlite.db.SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Database created - seed data will be added separately
        }

        @Override
        public void onOpen(@androidx.annotation.NonNull androidx.sqlite.db.SupportSQLiteDatabase db) {
            super.onOpen(db);
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    };

    public static void closeDatabase() {
        if (INSTANCE != null) {
            INSTANCE.close();
            INSTANCE = null;
        }
    }

    public static void destroyInstance() {
        if (INSTANCE != null) {
            INSTANCE.close();
            INSTANCE = null;
        }
    }
}