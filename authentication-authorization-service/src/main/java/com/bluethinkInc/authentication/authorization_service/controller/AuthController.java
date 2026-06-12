package com.bluethinkInc.authentication.authorization_service.controller;

import com.bluethinkInc.authentication.authorization_service.dto.LoginRequest;
import com.bluethinkInc.authentication.authorization_service.dto.RegisterRequest;
import com.bluethinkInc.authentication.authorization_service.dto.UserResponseEntity;
import com.bluethinkInc.authentication.authorization_service.dto.AuthResponse;
import com.bluethinkInc.authentication.authorization_service.model.User;
import com.bluethinkInc.authentication.authorization_service.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseEntity<User>> registerController(@RequestBody RegisterRequest registerRequest){
        UserResponseEntity<User> response =
                authService.registerService(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseEntity<AuthResponse>> loginController(@RequestBody LoginRequest loginRequest){
        UserResponseEntity<AuthResponse> response =
                authService.loginService(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
