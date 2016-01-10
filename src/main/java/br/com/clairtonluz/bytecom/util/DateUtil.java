package br.com.clairtonluz.bytecom.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author clairton
 */
public final class DateUtil {

    public static Date toDate(LocalDate localDate) {
        return toDate(localDate.atStartOfDay());
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date plusMonth(Date data, Integer value) {
        LocalDateTime localDateTime = DateUtil.toLocalDateTime(data).plusMonths(value);
        data = DateUtil.toDate(localDateTime);
        return data;
    }

}
