package com.rhsystem.api.rhsystemapi.core.security;

/**
 * TokenGenerator is a generic interface that defines the contract
 * for generating tokens based on a given input object.
 *
 * @param <T> the type of the token to be generated
 * @param <O> the type of the input object used to generate the token
 */
public interface TokenGenerator<T, O> {

    /**
     * Generates a token based on the input object provided.
     *
     * @param object the input object used to generate the token
     *
     * @return the generated token
     */
    T generateToken(O object);
}
