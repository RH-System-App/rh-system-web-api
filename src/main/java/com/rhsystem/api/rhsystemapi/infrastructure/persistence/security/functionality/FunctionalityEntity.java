package com.rhsystem.api.rhsystemapi.infrastructure.persistence.security.functionality;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rh_functionality")
public class FunctionalityEntity {

    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
