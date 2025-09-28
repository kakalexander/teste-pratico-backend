package com.testepratico.users;

import com.testepratico.users.model.User;

public interface UserService {

    User createUser(String username, String password, String email);

    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void validateUserCredentials(String username, String password);
}
