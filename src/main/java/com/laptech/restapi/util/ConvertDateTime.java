package com.laptech.restapi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Nhat Phi
 * @since 2022-11-22
 */
public class ConvertDateTime {
    private static final Logger logger = LoggerFactory.getLogger(ConvertDateTime.class);
    private static final DateTimeFormatter DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime getDateTimeFromString(String text) {
        if (text == null || text.equals("")) return null;
        try {
            return LocalDateTime.parse(text, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException err) {
            logger.info("[PARSE DATE TIME] {}", err.getLocalizedMessage());
            return null;
        }
    }

    public static LocalDateTime getDateTimeFromResultSet(ResultSet rs, String column) {
        try {
            if (rs.getString(column) == null) return null;
            return LocalDateTime.parse(rs.getString(column), DATE_TIME_PATTERN);
        } catch (DateTimeParseException | SQLException err) {
            logger.info("[PARSE DATE TIME FROM RESULT SET] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
