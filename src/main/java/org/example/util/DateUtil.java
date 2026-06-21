package org.example.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String toMonthAndYear(LocalDate localDate) {
        return localDate.getMonthValue() + "/" + localDate.getYear() % 100;
    }

    public static String toSimpleFormat(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm").format(localDateTime);
    }

}
