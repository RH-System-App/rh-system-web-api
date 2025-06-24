package com.rhsystem.api.rhsystemapi.application.user.http;

import com.rhsystem.api.rhsystemapi.application.user.http.presenters.UserPresenter;
import com.rhsystem.api.rhsystemapi.application.user.requests.CreateUserRequest;
import com.rhsystem.api.rhsystemapi.application.user.usecases.CreateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Controller for managing users in the system. This class provides endpoints of managing users.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "users", description = "Sevices of user")
public class UserController {


    /**
     * Use case for creating a new user.
     */
    private final CreateUserUseCase createUserUseCase;

    /**
     * Constructs a UserController with the specified CreateUserUseCase.
     *
     * @param createUserUseCase the use case for creating a new user
     */
    public UserController(final CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    /**
     * Endpoint to retrieve all users.
     *
     * @return a collection of UserPresenter objects representing all users
     */
    @GetMapping
    @Operation(summary = "Busca todos os usuários")
    @ApiResponse(responseCode = "200", description = "Lista de usuário retornada com sucesso.")
    public ResponseEntity<Collection<UserPresenter>> getUsers() {
        return ResponseEntity.ok(new ArrayList<>());
    }

    /**
     * Endpoint to create a new user.
     *
     * @param entity the request body containing user details for creation
     *
     * @return a ResponseEntity indicating the result of the creation operation
     */
    @PostMapping
    @Operation(summary = "Cria um novo usuário")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso.")
    public ResponseEntity<Void> postMethodName(@RequestBody @Valid CreateUserRequest entity) {
        var result = this.createUserUseCase.handle(entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                                             .buildAndExpand(result.getUsername()).toUri();
        return ResponseEntity.created(uri).build();
    }


}
