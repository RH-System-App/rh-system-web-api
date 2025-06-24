package com.rhsystem.api.rhsystemapi.infrastructure.persistence.user;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "RH_USER")
public class UserEntity {
    /**
     * The unique identifier for the user. This field is automatically generated as a UUID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "USER_ID")
    private UUID id;

    /**
     * The name of the user. This field is mapped to the "NAME" column in the database.
     */
    @Column(name = "NAME")
    private String name;

    /**
     * The email of the user. This field is mapped to the "EMAIL" column in the database.
     */
    @Column(name = "EMAIL", unique = true)
    private String email;

    /**
     * The username of the user. This field is mapped to the "USERNAME" column in the database.
     */
    @Column(name = "USERNAME", unique = true)
    private String userName;

    /**
     * The password of the user. This field is mapped to the "PASSWORD" column in the database.
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * Gets the unique identifier of the user.
     *
     * @return the unique identifier of the user
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id the unique identifier to set for the user
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the name to set for the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the email to set for the user
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Gets the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set for the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username of the user
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Sets the username of the user.
     *
     * @param userName the username to set for the user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
