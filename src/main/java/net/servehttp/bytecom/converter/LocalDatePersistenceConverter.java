package net.servehttp.bytecom.converter;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDatePersistenceConverter implements AttributeConverter<LocalDate, Date> {

  @Override
  public Date convertToDatabaseColumn(LocalDate attribute) {
    return java.sql.Date.valueOf(attribute);
  }

  @Override
  public LocalDate convertToEntityAttribute(Date dbData) {
    return dbData.toLocalDate();
  }
}
