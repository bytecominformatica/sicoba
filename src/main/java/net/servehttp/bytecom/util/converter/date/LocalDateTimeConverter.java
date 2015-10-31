package net.servehttp.bytecom.util.converter.date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@FacesConverter(value = "localDateTimeConverter", forClass = LocalDate.class)
public class LocalDateTimeConverter implements Converter {

    private static final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        if (value != null) {
            return LocalDateTime.parse(value, pattern);
        }
        return null;
    }

    public String getAsString(FacesContext ctx, UIComponent component, Object value) {

        if (value != null && !"".equals(value)) {
            LocalDateTime data = (LocalDateTime) value;
            if (data != null) {
                return data.format(pattern);
            }
        }
        return "";
    }
}
