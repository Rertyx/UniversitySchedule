package ru.dimov.universityschedule.model;

import jakarta.persistence.AttributeConverter;

import java.time.DayOfWeek;

public class DayOfWeekShortConverter implements AttributeConverter<DayOfWeek, Short> {
    @Override
    public Short convertToDatabaseColumn(DayOfWeek attribute) {
        return (short) attribute.getValue();
    }

    @Override
    public DayOfWeek convertToEntityAttribute(Short dbData) {
        return DayOfWeek.of(dbData);
    }
}
