package com.sportyshoes.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sportyshoes.dto.AdminLoginDTO;
import com.sportyshoes.dto.AdminPasswordChangeDTO;
import com.sportyshoes.dto.LoginUserDTO;
import com.sportyshoes.dto.RegisterUserDTO;
import com.sportyshoes.model.User;
import com.sportyshoes.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDTO input) {
        var user = new User();
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDTO input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getEmail(),
                input.getPassword()
            )
        );

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }
    
    public User authenticateAdmin(AdminLoginDTO adminLoginDto) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(adminLoginDto.getEmail(), adminLoginDto.getPassword())
        );

        User user = userRepository.findByEmail(adminLoginDto.getEmail()).orElseThrow();
        if (!user.isAdmin()) {
            throw new AccessDeniedException("User is not an admin");
        }
        return user;
    }

    public void changeAdminPassword(String email, AdminPasswordChangeDTO passwordChangeDto) {
        User admin = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));

        if (!admin.isAdmin()) {
            throw new AccessDeniedException("User is not an admin");
        }

        if (!passwordEncoder.matches(passwordChangeDto.getCurrentPassword(), admin.getPassword())) {
            throw new BadCredentialsException("Current password is incorrect");
        }

        admin.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        userRepository.save(admin);
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}
