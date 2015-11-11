package br.com.clairtonluz.bytecom.util.converter.date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Converter(autoApply = true)
public class LocalDatePersistenceConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate attribute) {
        if(attribute != null) {
            return java.sql.Date.valueOf(attribute);
        }
        return null;
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        return Optional.ofNullable(dbData).map(Date::toLocalDate).orElse(null);
    }
}
