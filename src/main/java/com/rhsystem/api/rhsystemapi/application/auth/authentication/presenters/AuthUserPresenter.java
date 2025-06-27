package com.rhsystem.api.rhsystemapi.application.auth.authentication.presenters;

public class AuthUserPresenter {

    private String token;
    private TokenType tokenType;

    public AuthUserPresenter() {
    }

    public AuthUserPresenter(String token, TokenType tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenType getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }


}
