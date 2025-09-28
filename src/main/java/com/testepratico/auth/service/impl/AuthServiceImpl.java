package com.testepratico.auth.service.impl;

import com.testepratico.auth.AuthService;
import com.testepratico.auth.dto.LoginRequest;
import com.testepratico.auth.dto.LoginResponse;
import com.testepratico.users.UserService;
import com.testepratico.auth.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

            String token = generateToken(loginRequest.getUsername());
            
            return LoginResponse.of(token, loginRequest.getUsername(), jwtExpiration);
            
        } catch (AuthenticationException e) {
            throw new RuntimeException("Credenciais inválidas");
        }
    }

    @Override
    public String generateToken(String username) {
        return jwtTokenProvider.generateToken(username);
    }

    @Override
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    @Override
    public String getUsernameFromToken(String token) {
        return jwtTokenProvider.getUsernameFromToken(token);
    }
}
