package com.testepratico.auth.security;

import com.testepratico.users.model.User;
import com.testepratico.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Carregando usuário por username: {}", username);
        
        try {
            User user = userService.findByUsername(username);
            log.debug("Usuário encontrado: {}", username);
            return user;
        } catch (RuntimeException e) {
            log.error("Usuário não encontrado: {}", username);
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
    }
}
