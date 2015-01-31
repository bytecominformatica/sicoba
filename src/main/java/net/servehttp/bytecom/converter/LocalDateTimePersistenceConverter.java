package net.servehttp.bytecom.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimePersistenceConverter implements
    AttributeConverter<LocalDateTime, Timestamp> {

  @Override
  public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
    return Timestamp.valueOf(attribute);
  }

  @Override
  public LocalDateTime convertToEntityAttribute(Timestamp dbData) {
    return dbData.toLocalDateTime();
  }
}
