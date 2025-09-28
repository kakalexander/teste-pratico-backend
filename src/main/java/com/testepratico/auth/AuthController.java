package com.testepratico.auth;

import com.testepratico.auth.dto.LoginRequest;
import com.testepratico.auth.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints para autenticação")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Autentica o usuário e retorna um token JWT")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Tentativa de login para usuário: {}", loginRequest.getUsername());
        
        LoginResponse response = authService.authenticate(loginRequest);
        
        log.info("Login realizado com sucesso para usuário: {}", loginRequest.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    @Operation(summary = "Validar token", description = "Valida se o token JWT é válido")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
        log.debug("Validando token JWT");
        
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        boolean isValid = authService.validateToken(jwt);
        
        return ResponseEntity.ok(isValid);
    }
}
