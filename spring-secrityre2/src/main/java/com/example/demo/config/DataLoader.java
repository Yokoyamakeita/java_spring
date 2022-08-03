package com.example.demo.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;
    private final SiteUserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // "admin"ユーザを用意します
        var user = new SiteUser();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("password"));

        userRepository.save(user);
    }
}
