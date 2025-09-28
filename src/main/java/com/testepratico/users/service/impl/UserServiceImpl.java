package com.testepratico.users.service.impl;

import com.testepratico.users.UserService;
import com.testepratico.users.model.User;
import com.testepratico.users.repository.UserRepository;
import com.testepratico.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(String username, String password, String email) {
        log.info("Criando usuário: {}", username);
        
        if (!ValidationUtils.isValidUsername(username)) {
            log.error("Username inválido: {}", username);
            throw new RuntimeException("Username deve conter apenas letras, números e underscore, com 3-50 caracteres");
        }
        
        if (!ValidationUtils.isValidEmail(email)) {
            log.error("Email inválido: {}", email);
            throw new RuntimeException("Email deve ter um formato válido");
        }
        
        if (!ValidationUtils.isValidPassword(password)) {
            log.error("Password inválido");
            throw new RuntimeException("Password deve ter pelo menos 6 caracteres");
        }
        
        if (existsByUsername(username)) {
            log.error("Username já existe: {}", username);
            throw new RuntimeException("Username já existe");
        }
        
        if (existsByEmail(email)) {
            log.error("Email já existe: {}", email);
            throw new RuntimeException("Email já existe");
        }

        String encodedPassword = passwordEncoder.encode(password);
        
        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .email(email)
                .build();

        User savedUser = userRepository.save(user);
        log.info("Usuário criado com sucesso. ID: {}", savedUser.getId());
        
        return savedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        log.debug("Buscando usuário por username: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Usuário não encontrado: {}", username);
                    return new RuntimeException("Usuário não encontrado");
                });
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void validateUserCredentials(String username, String password) {
        log.debug("Validando credenciais para usuário: {}", username);
        
        User user = findByUsername(username);
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.error("Senha inválida para usuário: {}", username);
            throw new RuntimeException("Credenciais inválidas");
        }
        
        log.debug("Credenciais válidas para usuário: {}", username);
    }
}
