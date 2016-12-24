package br.com.clairtonluz.sicoba.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author clairton
 */
public final class DateUtil {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static Date toDate(LocalDate localDate) {
        return toDate(localDate.atStartOfDay());
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDate toLocalDate(Date date) {
        return toLocalDateTime(date).toLocalDate();
    }

    public static Date plusMonth(Date data, Integer value) {
        LocalDateTime localDateTime = DateUtil.toLocalDateTime(data).plusMonths(value);
        data = DateUtil.toDate(localDateTime);
        return data;
    }

    /**
     * Return a String as yyyy-MM-dd
     *
     * @param vencimento
     * @return
     */
    public static String formatISO(Date vencimento) {
        return vencimento != null ? DATE_FORMAT.format(vencimento) : null;
    }

    public static Date parseDateISO(String dateString) {
        try {
            return dateString != null ? DATE_FORMAT.parse(dateString) : null;
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
