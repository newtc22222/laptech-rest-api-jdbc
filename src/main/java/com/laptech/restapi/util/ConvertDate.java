package com.laptech.restapi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Nhat Phi
 * @since 2023-02-28
 */
public class ConvertDate {
    private static final Logger logger = LoggerFactory.getLogger(ConvertDateTime.class);

    public static LocalDate getLocalDateFromString(String text) {
        if (text == null || text.equals("")) return null;
        try {
            return LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException err) {
            logger.info("[PARSE DATE] {}", err.getLocalizedMessage());
            return null;
        }
    }
}
