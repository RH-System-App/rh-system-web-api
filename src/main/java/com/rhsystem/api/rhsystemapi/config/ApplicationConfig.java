package com.rhsystem.api.rhsystemapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration class for mapping application properties under the "application" prefix.
 * This class is annotated with @Component and @ConfigurationProperties to allow
 * Spring Boot to inject the corresponding properties.
 * <p>
 * The configuration includes nested properties such as JWT configuration for handling
 * security and authentication in the application.
 */
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {

    /**
     * Represents the JWT-related properties for the application configuration.
     * This object provides access to values such as the JWT secret, which is typically
     * used for signing or validating JWT tokens in the application's security context.
     */
    private final JwtProperties jwt = new JwtProperties();

    private String version;

    /**
     * Retrieves the JWT-related properties from the application configuration.
     *
     * @return an instance of JwtProperties containing the JWT-specific configuration,
     * such as the secret used for signing or validating JWT tokens.
     */
    public JwtProperties getJwt() {
        return jwt;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Encapsulates configurable properties related to JWT (JSON Web Token)
     * for the application. These properties are typically defined in the application
     * configuration file and used for security-related operations.
     * <p>
     * The primary purpose of this class is to store and provide access to the
     * JWT secret key, which is crucial for signing and validating JWT tokens.
     */
    public static class JwtProperties {

        /**
         * Represents the secret key used for signing and validating JSON Web Tokens (JWT).
         * This property is a critical part of the application's security configuration,
         * as it ensures the integrity and authenticity of JWTs.
         * <p>
         * The secret key should be kept private and secure to prevent unauthorized access
         * and potential security breaches. It is typically loaded from an external configuration
         * file or environment variable to ensure its confidentiality.
         */
        private String secret;

        /**
         * Retrieves the secret key used for signing and validating JSON Web Tokens (JWT).
         *
         * @return the secret key as a String, which is a critical part of the
         * application's security configuration.
         */
        public String getSecret() {
            return secret;
        }

        /**
         * Sets the secret key used for signing and validating JSON Web Tokens (JWT).
         * This value is critical for ensuring the integrity and authenticity of JWTs.
         * The provided secret should be kept private and secure to prevent unauthorized access.
         *
         * @param secret the secret key as a String to be used for signing and validating JWTs.
         */
        public void setSecret(String secret) {
            this.secret = secret;
        }
    }
}
