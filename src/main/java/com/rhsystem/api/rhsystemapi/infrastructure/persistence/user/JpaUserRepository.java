package com.rhsystem.api.rhsystemapi.infrastructure.persistence.user;

import com.rhsystem.api.rhsystemapi.domain.user.User;
import com.rhsystem.api.rhsystemapi.domain.user.UserRepository;
import com.rhsystem.api.rhsystemapi.domain.user.UserStatus;
import com.rhsystem.api.rhsystemapi.domain.user.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {

    private final SpringDataJpaUserRepository springDataJpaUserRepository;
    private final UserMapper userMapper;

    public JpaUserRepository(SpringDataJpaUserRepository springDataJpaUserRepository, UserMapper userMapper) {
        this.springDataJpaUserRepository = springDataJpaUserRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User u) {
        UserEntity entity = springDataJpaUserRepository.save(toEntity(u));
        return toDomain(entity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return springDataJpaUserRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return springDataJpaUserRepository.existsByUserName(userName);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> opt = springDataJpaUserRepository.findByEmail(email);
        return opt.map(this::toDomain);
    }

    @Override
    public void updateStatus(User user, UserStatus status) {
        springDataJpaUserRepository.updateStatusByUserName(status, user.getUserName());
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return springDataJpaUserRepository.findByUserName(userName).map(this::toDomain);
    }

    private UserEntity toEntity(User user) {
        return userMapper.toEntity(user);
    }

    private User toDomain(UserEntity entity) {
        return userMapper.toDomain(entity);
    }


}
