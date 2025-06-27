package com.rhsystem.api.rhsystemapi.infrastructure.security;

import com.rhsystem.api.rhsystemapi.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceIml implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceIml(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUserName(username).orElse(null);
        return new UserDetailIml(user);
    }

}
