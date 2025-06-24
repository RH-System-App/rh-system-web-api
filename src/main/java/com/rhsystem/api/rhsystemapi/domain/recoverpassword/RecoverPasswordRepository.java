package com.rhsystem.api.rhsystemapi.domain.recoverpassword;

import com.rhsystem.api.rhsystemapi.domain.user.User;

import java.util.Optional;
import java.util.UUID;

public interface RecoverPasswordRepository {

    Optional<RecoverPassword> findByRecoverCode(UUID recoverCode);

    void delete(RecoverPassword recoverPassword);

    Optional<RecoverPassword> findByUser(User user);

    RecoverPassword save(RecoverPassword recoverPassword);
}
