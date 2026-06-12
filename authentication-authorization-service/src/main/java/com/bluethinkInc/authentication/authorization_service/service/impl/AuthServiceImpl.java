package com.bluethinkInc.authentication.authorization_service.service.impl;

import com.bluethinkInc.authentication.authorization_service.dto.LoginRequest;
import com.bluethinkInc.authentication.authorization_service.dto.RegisterRequest;
import com.bluethinkInc.authentication.authorization_service.dto.UserResponseEntity;
import com.bluethinkInc.authentication.authorization_service.enums.Role;
import com.bluethinkInc.authentication.authorization_service.model.User;
import com.bluethinkInc.authentication.authorization_service.repo.UserRepo;
import com.bluethinkInc.authentication.authorization_service.service.AuthService;
import com.bluethinkInc.authentication.authorization_service.config.JwtUtil;
import com.bluethinkInc.authentication.authorization_service.dto.AuthResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepo repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepo repo, JwtUtil jwtUtil){
        this.repo = repo;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserResponseEntity<User> registerService(RegisterRequest registerRequest) {
        if(repo.existsByEmail(registerRequest.getEmail())){
            throw new RuntimeException("Email already exists!!!");
        }
        User user = new User();
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setRole(Role.CUSTOMER);
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        repo.save(user);
        return new UserResponseEntity<>(
                "Registered Successfully",
                200,
                user,
                LocalDateTime.now()
        );
    }

    @Override
    public UserResponseEntity<AuthResponse> loginService(LoginRequest loginRequest) {
        var userOpt = repo.findByEmail(loginRequest.getEmail());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid credentials");
        }

        User user = userOpt.get();
        if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new RuntimeException("User account is inactive");
        }

        String token = null;
        // if client provided an existing token, validate and reuse it if still valid and belongs to same user
        if (loginRequest.getExistingToken() != null && !loginRequest.getExistingToken().isBlank()) {
            String existing = loginRequest.getExistingToken().trim();
            if (jwtUtil.validateToken(existing)) {
                String subject = jwtUtil.getSubjectFromToken(existing);
                if (subject != null && subject.equalsIgnoreCase(user.getEmail())) {
                    token = existing; // reuse
                }
            }
        }

        if (token == null) {
            token = jwtUtil.generateToken(user.getEmail());
        }

        AuthResponse authResponse = new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole());

        return new UserResponseEntity<>(
                "Login successful",
                200,
                authResponse,
                java.time.LocalDateTime.now()
        );
    }
}
