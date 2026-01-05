package com.tm.elearningtm.database;

import androidx.room.TypeConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Converters {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @TypeConverter
    public static LocalDateTime fromString(String value) {
        if (value == null) {
            return null;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            return LocalDateTime.parse(value, formatter);
        }
        return null;
    }

    @TypeConverter
    public static String fromLocalDateTime(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            return date.format(formatter);
        }
        return null;
    }
}