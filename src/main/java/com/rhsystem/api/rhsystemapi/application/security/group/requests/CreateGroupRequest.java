package com.rhsystem.api.rhsystemapi.application.security.group.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Collection;

public class CreateGroupRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotEmpty(message = "Functionalities is required")
    private Collection<String> functionalities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<String> getFunctionalities() {
        return functionalities;
    }

    public void setFunctionalities(Collection<String> functionalities) {
        this.functionalities = functionalities;
    }
}
