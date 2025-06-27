package com.rhsystem.api.rhsystemapi.infrastructure.config;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime instant,
        int status,
        String error,
        String message,
        String path
) {
}