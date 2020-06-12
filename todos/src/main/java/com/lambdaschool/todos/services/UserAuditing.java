package com.lambdaschool.todos.services;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAuditing implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String username;
        username = "llama";
        return Optional.of(username);
    }
}
