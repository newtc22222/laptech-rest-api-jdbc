package com.laptech.restapi.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertDateTime {
    private static final DateTimeFormatter DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime getDateTimeFromString(String text) {
        return LocalDateTime.parse(text, DATE_TIME_PATTERN);
    }

    public static LocalDateTime getDateTimeFromResultSet(ResultSet rs, String column) throws SQLException {
        return LocalDateTime.parse(rs.getString(column), DATE_TIME_PATTERN);
    }
}
