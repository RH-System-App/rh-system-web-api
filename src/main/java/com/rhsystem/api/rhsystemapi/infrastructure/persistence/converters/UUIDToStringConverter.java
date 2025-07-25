package com.rhsystem.api.rhsystemapi.infrastructure.persistence.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

/**
 * A JPA attribute converter that converts a UUID object to a String representation
 * for database storage and vice versa. This class is annotated with {@code @Converter}
 * to indicate that it will be used for automatic conversion between UUID and String types.
 * <p>
 * This converter ensures that UUIDs can be stored as Strings in the database while
 * maintaining their type integrity within the application.
 * <p>
 * Methods:
 * - {@code convertToDatabaseColumn(UUID uuid)}: Converts a {@code UUID} to its String representation
 * suitable for storing in the database. If the input is {@code null}, the method returns {@code null}.
 * <p>
 * - {@code convertToEntityAttribute(String dbData)}: Converts a database String representation back
 * to a {@code UUID} object. If the input is {@code null}, the method returns {@code null}.
 */
@Converter(autoApply = true)
public class UUIDToStringConverter implements AttributeConverter<UUID, String> {
    
    @Override
    public String convertToDatabaseColumn(UUID uuid) {
        return uuid == null ? null : uuid.toString();
    }

    @Override
    public UUID convertToEntityAttribute(String dbData) {
        return dbData == null ? null : UUID.fromString(dbData);
    }
}
