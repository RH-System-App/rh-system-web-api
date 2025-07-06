package com.rhsystem.api.rhsystemapi.domain.security.group;

import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;
import com.rhsystem.api.rhsystemapi.domain.security.functionality.Functionality;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Represents a group within the system. A group is a domain entity with a unique identifier
 * and a name attribute to define its identity and properties.
 * <p>
 * This class extends {@code DomainEntity}, inheriting attributes related to unique entity identification.
 */
public class Group extends DomainEntity<UUID> {

    private String name;

    private Collection<Functionality> functionalities = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Functionality> getFunctionalities() {
        return functionalities;
    }

    public void setFunctionalities(Collection<Functionality> functionalities) {
        this.functionalities = functionalities;
    }

    public void addFunctionality(Functionality functionality) {
        this.functionalities.add(functionality);
    }

    public void removeFunctionality(Functionality functionality) {
        this.functionalities.remove(functionality);
    }

}
