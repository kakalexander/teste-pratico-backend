package com.testepratico.config;

import com.testepratico.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Verificar se já existe um usuário admin
            if (!userService.existsByUsername("admin")) {
                log.info("Criando usuário admin padrão...");
                userService.createUser("admin", "admin123", "admin@testepratico.com");
                log.info("Usuário admin criado com sucesso!");
            } else {
                log.info("Usuário admin já existe.");
            }
        } catch (Exception e) {
            log.error("Erro ao inicializar dados: {}", e.getMessage());
        }
    }
}
