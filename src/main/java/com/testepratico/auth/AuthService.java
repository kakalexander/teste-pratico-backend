package com.testepratico.auth;

import com.testepratico.auth.dto.LoginRequest;
import com.testepratico.auth.dto.LoginResponse;

public interface AuthService {

    LoginResponse authenticate(LoginRequest loginRequest);

    String generateToken(String username);

    boolean validateToken(String token);

    String getUsernameFromToken(String token);
}
