package com.sportyshoes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sportyshoes.model.User;
import com.sportyshoes.repository.UserRepository;

@Component
public class InitializeUsersSetup implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin user exists, if not create one
        if (userRepository.findByEmail("admin@sportyshoes.com").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("adminPassword")); // Change this to a secure password
            adminUser.setEmail("admin@sportyshoes.com");
            adminUser.setAdmin(true);
            userRepository.save(adminUser);
            System.out.println("Admin user created");
        }

        // You can add more initial users here if needed
        // For example, a regular user:
        if (userRepository.findByEmail("user@sportyshoes.com").isEmpty()) {
            User regularUser = new User();
            regularUser.setUsername("user");
            regularUser.setPassword(passwordEncoder.encode("userPassword")); // Change this to a secure password
            regularUser.setEmail("user@sportyshoes.com");
            regularUser.setAdmin(false);
            userRepository.save(regularUser);
            System.out.println("Regular user created");
        }
    }
}
