package com.rhsystem.api.rhsystemapi.infrastructure.persistence.security.group;

import com.rhsystem.api.rhsystemapi.infrastructure.persistence.converters.UUIDToStringConverter;
import com.rhsystem.api.rhsystemapi.infrastructure.persistence.security.functionality.FunctionalityEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.Collection;
import java.util.UUID;

/**
 * Represents a group entity within the system.
 * This entity is mapped to the "RH_GROUP" table in the database.
 * It contains information about groups, including their unique identifier and name.
 */
@Entity
@Table(name = "RH_GROUP")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "group_id", length = 36)
    @Convert(converter = UUIDToStringConverter.class)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    /**
     * The name of the group.
     * This field is mapped to the "group_name" column in the database.
     * It cannot be null and has a maximum length of 120 characters.
     */
    @Column(name = "group_name", nullable = false, length = 120)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "rh_group_functionality",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "functionality_code")

    )
    private Collection<FunctionalityEntity> functionalities;


    /**
     * Retrieves the unique identifier of the group entity.
     *
     * @return the unique identifier (UUID) of the group entity
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the group entity.
     *
     * @param id the unique identifier (UUID) to be assigned to the group entity
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the group.
     *
     * @return the name of the group
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the group.
     *
     * @param name the name to be assigned to the group
     */
    public void setName(String name) {
        this.name = name;
    }

    public Collection<FunctionalityEntity> getFunctionalities() {
        return functionalities;
    }

    public void setFunctionalities(Collection<FunctionalityEntity> functionalities) {
        this.functionalities = functionalities;
    }
}
