package com.laptech.restapi.util;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @since 2023-06-20
 * @see <a href="https://stackoverflow.com/questions/2689379/how-to-get-a-list-of-dates-between-two-dates-in-java">Build list dates</a>
 */
public class BuildDateTimeCollection {
    // Get list 30 days
    public static Collection<LocalDate> getListDayFromDate(LocalDate startDate, Integer limit) {
        return Stream
                .iterate(Optional.ofNullable(startDate).orElse(LocalDate.now()), localDate -> localDate.plusDays(1))
                .limit(Optional.ofNullable(limit).orElse(30))
                .collect(Collectors.toList());
    }

    public static Collection<LocalDate> getListDayToDate(LocalDate endDate, Integer limit) {
        List<LocalDate> dateCollection = Stream
                .iterate(Optional.ofNullable(endDate).orElse(LocalDate.now()), localDate -> localDate.minusDays(1))
                .limit(Optional.ofNullable(limit).orElse(30))
                .collect(Collectors.toList());
        Collections.reverse(dateCollection); // right order
        return dateCollection;
    }

    public static Collection<LocalDate> getListMonthFromDate(LocalDate startDate, Integer limit) {
        return Stream
                .iterate(Optional.ofNullable(startDate).orElse(LocalDate.now()), localDate -> localDate.plusMonths(1))
                .limit(Optional.ofNullable(limit).orElse(12))
                .collect(Collectors.toList());
    }

    public static Collection<LocalDate> getListMonthToDate(LocalDate endDate, Integer limit) {
        List<LocalDate> dateCollection = Stream
                .iterate(Optional.ofNullable(endDate).orElse(LocalDate.now()), localDate -> localDate.minusMonths(1))
                .limit(Optional.ofNullable(limit).orElse(12))
                .collect(Collectors.toList());
        Collections.reverse(dateCollection); // right order
        return dateCollection;
    }
}
