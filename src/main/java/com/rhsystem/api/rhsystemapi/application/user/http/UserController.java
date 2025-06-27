package com.rhsystem.api.rhsystemapi.application.user.http;

import com.rhsystem.api.rhsystemapi.application.user.presenters.UserPresenter;
import com.rhsystem.api.rhsystemapi.application.user.requests.CreateUserRequest;
import com.rhsystem.api.rhsystemapi.application.user.usecases.BlockUserUseCase;
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
     * Use case for blocking a user account. This instance is responsible for handling
     * the business logic related to locking a user's account based on their username.
     * It is utilised in user-related operations where account status management
     * is required.
     */
    private final BlockUserUseCase blockUserUseCase;

    /**
     * Constructs a UserController with the specified CreateUserUseCase.
     *
     * @param createUserUseCase the use case for creating a new user
     */
    public UserController(final CreateUserUseCase createUserUseCase, BlockUserUseCase blockUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.blockUserUseCase = blockUserUseCase;
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
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserRequest entity) {
        var result = this.createUserUseCase.handle(entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                                             .buildAndExpand(result.getUsername()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @PutMapping("{username}/lock")
    @Operation(summary = "Realiza o bloqueio de um usuario")
    @ApiResponse(responseCode = "204", description = "Usuário bloqueado com sucesso")
    public ResponseEntity<Void> blockUser(@PathVariable("username") String username) {
        this.blockUserUseCase.handle(username);
        return ResponseEntity.noContent().build();
    }


}
