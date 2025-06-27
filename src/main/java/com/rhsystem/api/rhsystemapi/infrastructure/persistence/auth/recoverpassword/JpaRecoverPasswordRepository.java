package com.rhsystem.api.rhsystemapi.infrastructure.persistence.auth.recoverpassword;

import com.rhsystem.api.rhsystemapi.core.ReferenceFinder;
import com.rhsystem.api.rhsystemapi.domain.recoverpassword.RecoverPassword;
import com.rhsystem.api.rhsystemapi.domain.recoverpassword.RecoverPasswordRepository;
import com.rhsystem.api.rhsystemapi.domain.user.User;
import com.rhsystem.api.rhsystemapi.infrastructure.mapper.RecoverPasswordMapper;
import com.rhsystem.api.rhsystemapi.infrastructure.mapper.UserMapper;
import com.rhsystem.api.rhsystemapi.infrastructure.persistence.user.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaRecoverPasswordRepository implements RecoverPasswordRepository {

    private final SpringDataJpaRecoverPasswordRepository springDataJpaRecoverPasswordRepository;
    private final RecoverPasswordMapper recoverPasswordMapper;
    private final UserMapper userMapper;

    private final ReferenceFinder<UserEntity, User> referenceFinder;

    public JpaRecoverPasswordRepository(SpringDataJpaRecoverPasswordRepository springDataJpaRecoverPasswordRepository, RecoverPasswordMapper recoverPasswordMapper, UserMapper userMapper, ReferenceFinder<UserEntity, User> referenceFinder) {
        this.springDataJpaRecoverPasswordRepository = springDataJpaRecoverPasswordRepository;
        this.recoverPasswordMapper = recoverPasswordMapper;
        this.userMapper = userMapper;
        this.referenceFinder = referenceFinder;
    }

    @Override
    public Optional<RecoverPassword> findByRecoverCode(UUID recoverCode) {
        Optional<RecoverPasswordEntity> entity = springDataJpaRecoverPasswordRepository.findByRecoverCode(recoverCode);
        return entity.map(recoverPasswordMapper::toDomain);
    }

    @Override
    public void delete(RecoverPassword recoverPassword) {
        springDataJpaRecoverPasswordRepository.delete(recoverPasswordMapper.toEntity(recoverPassword));
    }

    @Override
    public Optional<RecoverPassword> findByUser(User user) {
        Optional<RecoverPasswordEntity> entity =
                springDataJpaRecoverPasswordRepository.findByUser(userMapper.toEntity(user));
        return entity.map(recoverPasswordMapper::toDomain);
    }

    @Override
    public RecoverPassword save(RecoverPassword recoverPassword) {
        RecoverPasswordEntity entity = recoverPasswordMapper.toEntity(recoverPassword);
        entity.setUser(referenceFinder.findReference(recoverPassword.getUser()));
        entity = springDataJpaRecoverPasswordRepository.save(entity);
        return recoverPasswordMapper.toDomain(entity);
    }
}
