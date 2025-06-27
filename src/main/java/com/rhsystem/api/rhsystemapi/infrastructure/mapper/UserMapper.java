package com.rhsystem.api.rhsystemapi.infrastructure.mapper;

import com.rhsystem.api.rhsystemapi.core.ObjectMapper;
import com.rhsystem.api.rhsystemapi.core.valueobject.EntityKey;
import com.rhsystem.api.rhsystemapi.domain.user.User;
import com.rhsystem.api.rhsystemapi.infrastructure.persistence.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends ObjectMapper<User, UserEntity> {

    @Override
    public User toDomain(UserEntity entity) {
        User u = new User();
        u.setKey(EntityKey.of(entity.getId()));
        u.setName(entity.getName());
        u.setEmail(entity.getEmail());
        u.setUserName(entity.getUserName());
        u.setPassword(entity.getPassword());
        u.setStatus(entity.getStatus());
        return u;
    }

    @Override
    public UserEntity toEntity(User domain) {
        UserEntity entity = new UserEntity();
        if (domain.getKey() != null) {
            entity.setId(domain.getKey().getValue());
        }
        entity.setName(domain.getName());
        entity.setEmail(domain.getEmail());
        entity.setUserName(domain.getUserName());
        entity.setPassword(domain.getPassword());
        entity.setStatus(domain.getStatus());
        return entity;
    }

}

