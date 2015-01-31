package net.servehttp.bytecom.converter.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "localDateConverter", forClass = LocalDate.class)
public class LocalDateConverter implements Converter {

  private static final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
    if (value != null) {
      return LocalDate.parse(value, pattern);
    }
    return null;
  }

  public String getAsString(FacesContext ctx, UIComponent component, Object value) {

    if (value != null && !"".equals(value)) {
      LocalDate data = (LocalDate) value;
      if (data != null) {
        return data.format(pattern);
      }
    }
    return "";
  }
}
