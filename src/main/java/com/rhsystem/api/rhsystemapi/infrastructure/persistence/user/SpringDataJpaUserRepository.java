package com.rhsystem.api.rhsystemapi.infrastructure.persistence.user;

import com.rhsystem.api.rhsystemapi.domain.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataJpaUserRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    Optional<UserEntity> findByEmail(String email);

    @Modifying
    @Query("update UserEntity u set u.status = ?1 where u.userName = ?2")
    void updateStatusByUserName(UserStatus status, String userName);

    Optional<UserEntity> findByUserName(String userName);
}
