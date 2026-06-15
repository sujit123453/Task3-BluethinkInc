package com.bluethinkInc.authentication.authorization_service.controller;

import com.bluethinkInc.authentication.authorization_service.dto.LoginRequest;
import com.bluethinkInc.authentication.authorization_service.dto.RegisterRequest;
import com.bluethinkInc.authentication.authorization_service.dto.UserResponseEntity;
import com.bluethinkInc.authentication.authorization_service.dto.AuthResponse;
import com.bluethinkInc.authentication.authorization_service.dto.OtpResponse;
import com.bluethinkInc.authentication.authorization_service.dto.SendOtpRequest;
import com.bluethinkInc.authentication.authorization_service.dto.VerifyOtpRequest;
import com.bluethinkInc.authentication.authorization_service.model.User;
import com.bluethinkInc.authentication.authorization_service.service.AuthService;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserResponseEntity<AuthResponse>> loginController(@Valid @RequestBody LoginRequest loginRequest){
        UserResponseEntity<AuthResponse> response =
                authService.loginWithPhoneService(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<UserResponseEntity<OtpResponse>> sendOtpController(@Valid @RequestBody SendOtpRequest request){
        UserResponseEntity<OtpResponse> response =
                authService.sendOtpService(request.getPhone());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<UserResponseEntity<AuthResponse>> verifyOtpController(@Valid @RequestBody VerifyOtpRequest request){
        UserResponseEntity<AuthResponse> response =
                authService.verifyOtpService(request.getPhone(), request.getOtp());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
