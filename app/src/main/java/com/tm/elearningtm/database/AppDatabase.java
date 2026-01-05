package com.tm.elearningtm.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.tm.elearningtm.classes.Curs;
import com.tm.elearningtm.classes.MaterialCurs;
import com.tm.elearningtm.classes.SubmisieStudent;
import com.tm.elearningtm.classes.Tema;
import com.tm.elearningtm.classes.User;

@Database(
        entities = {
                User.class,           // Table: users
                Curs.class,           // Table: courses
                Tema.class,           // Table: teme
                MaterialCurs.class,   // Table: materiale
                SubmisieStudent.class // Table: submisii
        },
        version = 1,  // Increment this when you change the schema
        exportSchema = false  // Set to true in production to keep schema history
)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    // Singleton instance
    private static volatile AppDatabase INSTANCE;
    private static final String DATABASE_NAME = "elearning_database.db";

    // ========== DAO ACCESSORS ==========
    // Room will implement these abstract methods
    public abstract UserDao userDao();
    public abstract CursDao cursDao();
    public abstract TemaDao temaDao();
    public abstract MaterialDao materialDao();
    public abstract SubmisieDao submisieDao();

    // ========== SINGLETON PATTERN ==========

    /**
     * Get the database instance. Creates it if it doesn't exist.
     * Thread-safe using double-checked locking.
     *
     * @param context Application context (use getApplicationContext())
     * @return The singleton AppDatabase instance
     */
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
                // FOR DEVELOPMENT ONLY - Allows queries on main thread
                .allowMainThreadQueries()

                // FOR DEVELOPMENT ONLY - migrates the database if the schema changes
                .fallbackToDestructiveMigration()

                // FOR DEVELOPMENT ONLY - pre-populate database on creation
                // .addCallback(DATABASE_CALLBACK)

                .build();
    }

    // ========== DATABASE CALLBACKS (Optional) ==========
    private static final RoomDatabase.Callback DATABASE_CALLBACK = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@androidx.annotation.NonNull androidx.sqlite.db.SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

        @Override
        public void onOpen(@androidx.annotation.NonNull androidx.sqlite.db.SupportSQLiteDatabase db) {
            super.onOpen(db);
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    };

    // ========== UTILITY METHODS ==========
    public static void closeDatabase() {
        if (INSTANCE != null) {
            INSTANCE.close();
            INSTANCE = null;
        }
    }

    // Methods mostly used in testing
    public static void destroyInstance() {
        if (INSTANCE != null) {
            INSTANCE.close();
            INSTANCE = null;
        }
    }

    public void clearAllTables() {
        if (INSTANCE != null) {
            INSTANCE.clearAllTables();
        }
    }
}