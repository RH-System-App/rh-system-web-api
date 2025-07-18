package com.rhsystem.api.rhsystemapi.application.auth.authentication.usecase;

import com.rhsystem.api.rhsystemapi.application.auth.authentication.exceptions.CredentialsNotMatch;
import com.rhsystem.api.rhsystemapi.application.auth.authentication.presenters.AuthUserPresenter;
import com.rhsystem.api.rhsystemapi.application.auth.authentication.presenters.TokenType;
import com.rhsystem.api.rhsystemapi.application.auth.authentication.requests.AuthRequest;
import com.rhsystem.api.rhsystemapi.core.security.TokenGenerator;
import com.rhsystem.api.rhsystemapi.domain.user.User;
import com.rhsystem.api.rhsystemapi.domain.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticateUserUseCase {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenGenerator<String, User> tokenGenerator;

    public AuthenticateUserUseCase(PasswordEncoder passwordEncoder, UserRepository userRepository, TokenGenerator<String, User> tokenGenerator) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenGenerator = tokenGenerator;
    }

    public AuthUserPresenter handle(AuthRequest request) {
        var optionalUser = userRepository.findByEmail(request.getUser());

        if (optionalUser.isEmpty()) {
            optionalUser = userRepository.findByUserName(request.getUser());
            if (optionalUser.isEmpty()) {
                throw new CredentialsNotMatch();
            }
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CredentialsNotMatch();
        }

        String token = tokenGenerator.generateToken(user);

        AuthUserPresenter presenter = new AuthUserPresenter();
        presenter.setToken(token);
        presenter.setTokenType(TokenType.BEARER);

        return presenter;
    }
}
