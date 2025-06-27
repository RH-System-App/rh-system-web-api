package com.rhsystem.api.rhsystemapi.application.auth.authentication.http;

import com.rhsystem.api.rhsystemapi.application.auth.authentication.presenters.AuthUserPresenter;
import com.rhsystem.api.rhsystemapi.application.auth.authentication.requests.AuthRequest;
import com.rhsystem.api.rhsystemapi.application.auth.authentication.usecase.AuthenticateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "auth", description = "Sevices of authentication")
public class AuthController {

    private final AuthenticateUserUseCase authenticateUserUseCase;

    public AuthController(AuthenticateUserUseCase authenticateUserUseCase) {
        this.authenticateUserUseCase = authenticateUserUseCase;
    }

    @PostMapping
    @Operation(summary = "Authenticate user")
    @ApiResponse(responseCode = "201", description = "User authenticated successfully.")
    public ResponseEntity<AuthUserPresenter> authenticate(@RequestBody @Valid AuthRequest request) {
        var result = this.authenticateUserUseCase.handle(request);
        return ResponseEntity.status(201).body(result);
    }

}
