package br.com.clairtonluz.bytecom.util.converter.date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Converter(autoApply = true)
public class LocalDateTimePersistenceConverter implements
        AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
        if(attribute != null) {
            return Timestamp.valueOf(attribute);
        }
        return null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp dbData) {
        return Optional.ofNullable(dbData).map(Timestamp::toLocalDateTime).orElse(null);
    }
}
