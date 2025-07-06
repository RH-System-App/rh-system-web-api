package com.rhsystem.api.rhsystemapi.infrastructure.http.security;

import com.rhsystem.api.rhsystemapi.application.security.group.requests.CreateGroupRequest;
import com.rhsystem.api.rhsystemapi.application.security.group.usecases.CreateGroupUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/security/group")
@Tag(name = "group", description = "Sevices of groups")
@Tag(name = "security", description = "Sevices of security")
public class GroupController {

    private final CreateGroupUseCase createGroupUseCase;

    public GroupController(CreateGroupUseCase createGroupUseCase) {
        this.createGroupUseCase = createGroupUseCase;
    }

    @PostMapping
    @Operation(summary = "Create group")
    @ApiResponse(responseCode = "201", description = "Group created successfully.")
    public ResponseEntity<Void> createGroup(@RequestBody @Valid CreateGroupRequest request) {
        var result = this.createGroupUseCase.handle(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                                             .path("{groupid}")
                                             .buildAndExpand(result.getGroupId())
                                             .toUri();
        return ResponseEntity.created(uri).build();
    }
}
