package com.rhsystem.api.rhsystemapi.application.auth.recoverpassword.http;

import com.rhsystem.api.rhsystemapi.application.auth.recoverpassword.requests.ChangePasswordRequest;
import com.rhsystem.api.rhsystemapi.application.auth.recoverpassword.requests.RecoverPasswordRequest;
import com.rhsystem.api.rhsystemapi.application.auth.recoverpassword.usecases.ChangePasswordUseCase;
import com.rhsystem.api.rhsystemapi.application.auth.recoverpassword.usecases.RecoverPasswordUseCase;
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
@RequestMapping("/api/auth/recover-password")
@Tag(name = "auth")
@Tag(name = "recover-password", description = "Sevices of recover password")
public class RecoverPasswordController {

    private final RecoverPasswordUseCase recoverPasswordUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;

    public RecoverPasswordController(RecoverPasswordUseCase recoverPasswordUseCase,
                                     ChangePasswordUseCase changePasswordUseCase) {
        this.recoverPasswordUseCase = recoverPasswordUseCase;
        this.changePasswordUseCase = changePasswordUseCase;
    }


    @PostMapping
    @Operation(summary = "Recover password")
    @ApiResponse(responseCode = "204", description = "Password recovery request processed successfully.")
    private ResponseEntity<Void> recoverPassword(@RequestBody @Valid RecoverPasswordRequest request) {
        this.recoverPasswordUseCase.handle(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/change-password")
    @Operation(summary = "Change password")
    @ApiResponse(responseCode = "204", description = "Password changed successfully.")
    private ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        this.changePasswordUseCase.handle(request);
        return ResponseEntity.noContent().build();
    }


}
