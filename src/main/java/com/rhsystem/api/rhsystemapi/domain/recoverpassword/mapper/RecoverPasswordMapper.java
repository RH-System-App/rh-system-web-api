package com.rhsystem.api.rhsystemapi.domain.recoverpassword.mapper;

import com.rhsystem.api.rhsystemapi.core.ObjectMapper;
import com.rhsystem.api.rhsystemapi.core.valueobject.EntityKey;
import com.rhsystem.api.rhsystemapi.domain.recoverpassword.RecoverPassword;
import com.rhsystem.api.rhsystemapi.domain.user.mapper.UserMapper;
import com.rhsystem.api.rhsystemapi.infrastructure.persistence.auth.recoverpassword.RecoverPasswordEntity;
import org.springframework.stereotype.Component;

@Component
public class RecoverPasswordMapper extends ObjectMapper<RecoverPassword, RecoverPasswordEntity> {

    private final UserMapper userMapper;


    public RecoverPasswordMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public RecoverPassword toDomain(RecoverPasswordEntity entity) {
        RecoverPassword recoverPassword = new RecoverPassword();
        if (entity.getId() != null) {
            recoverPassword.setKey(EntityKey.of(entity.getId()));
        }
        recoverPassword.setUser(userMapper.toDomain(entity.getUser()));
        recoverPassword.setRecoverCode(entity.getRecoverCode());
        recoverPassword.setExpirationDate(entity.getExpirationDate());
        return recoverPassword;
    }

    @Override
    public RecoverPasswordEntity toEntity(RecoverPassword domain) {
        RecoverPasswordEntity entity = new RecoverPasswordEntity();
        entity.setUser(userMapper.toEntity(domain.getUser()));
        entity.setRecoverCode(domain.getRecoverCode());
        entity.setExpirationDate(domain.getExpirationDate());
        if (domain.getKey() != null) {
            entity.setId(domain.getKey().getValue());
        }
        return entity;
    }

}
