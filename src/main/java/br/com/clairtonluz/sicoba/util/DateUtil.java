package br.com.clairtonluz.sicoba.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author clairton
 */
public final class DateUtil {

    public static final DateTimeFormatter DATE_PT_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter DATE_TIME_PT_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static final SimpleDateFormat DATE_ISO = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATETIME_ISO = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date toDate(LocalDate localDate) {
        return localDate != null ? toDate(localDate.atStartOfDay()) : null;
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return localDateTime != null ? Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
    }

    public static LocalDate toLocalDate(Date date) {
        return date != null ? toLocalDateTime(date).toLocalDate() : null;
    }

    public static Date plusMonth(Date data, Integer value) {
        LocalDateTime localDateTime = DateUtil.toLocalDateTime(data).plusMonths(value);
        data = DateUtil.toDate(localDateTime);
        return data;
    }

    /**
     * Return a String as yyyy-MM-dd
     *
     * @param data
     * @return
     */
    public static String formatISO(Date data) {
        return data != null ? DATE_ISO.format(data) : null;
    }

    public static String formatDate(LocalDate data) {
        return data != null ? DATE_PT_BR.format(data) : null;
    }

    public static Date parseDateISO(String dateString) {
        try {
            return dateString != null ? DATE_ISO.parse(dateString) : null;
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Date parseDatetimeISO(String datetimeString) {
        try {
            return datetimeString != null ? DATE_ISO.parse(datetimeString) : null;
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static boolean isPast(Date date) {
        LocalDate today = LocalDate.now();
        LocalDate otherDate = toLocalDate(date);

        return otherDate.isBefore(today);
    }
}
