package com.testepratico.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String type;
    private Long expiresIn;
    private String username;

    public static LoginResponse of(String token, String username, Long expiresIn) {
        return LoginResponse.builder()
                .token(token)
                .type("Bearer")
                .expiresIn(expiresIn)
                .username(username)
                .build();
    }
}
