package com.rhsystem.api.rhsystemapi.domain.security.functionality;

import com.rhsystem.api.rhsystemapi.core.valueobject.DomainEntity;

public class Functionality extends DomainEntity<String> {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
