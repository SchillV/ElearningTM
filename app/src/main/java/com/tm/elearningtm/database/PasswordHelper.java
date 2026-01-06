package com.tm.elearningtm.database;

import android.util.Log;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordHelper {

    private static final String TAG = "PasswordHelper";

    private static final int BCRYPT_COST = 12;

    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        try {
            long startTime = System.currentTimeMillis();

            // Hash the password with BCrypt
            String hashedPassword = BCrypt.withDefaults()
                    .hashToString(BCRYPT_COST, plainPassword.toCharArray());

            long duration = System.currentTimeMillis() - startTime;
            Log.d(TAG, "Password hashed in " + duration + "ms");

            return hashedPassword;

        } catch (Exception e) {
            Log.e(TAG, "Error hashing password", e);
            throw new RuntimeException("Failed to hash password", e);
        }
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            Log.w(TAG, "Plain password is null or empty");
            return false;
        }

        if (hashedPassword == null || hashedPassword.isEmpty()) {
            Log.w(TAG, "Hashed password is null or empty");
            return false;
        }

        try {
            long startTime = System.currentTimeMillis();

            // Verify the password
            BCrypt.Result result = BCrypt.verifyer()
                    .verify(plainPassword.toCharArray(), hashedPassword);

            long duration = System.currentTimeMillis() - startTime;
            Log.d(TAG, "Password verified in " + duration + "ms");

            return result.verified;

        } catch (Exception e) {
            Log.e(TAG, "Error verifying password", e);
            return false;
        }
    }

}