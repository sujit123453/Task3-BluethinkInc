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
import com.bluethinkInc.authentication.authorization_service.dto.OtpResponse;
import com.bluethinkInc.authentication.authorization_service.service.OtpService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepo repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final JwtUtil jwtUtil;
    private final OtpService otpService;

    public AuthServiceImpl(UserRepo repo, JwtUtil jwtUtil, OtpService otpService){
        this.repo = repo;
        this.jwtUtil = jwtUtil;
        this.otpService = otpService;
    }

    @Override
    public UserResponseEntity<User> registerService(RegisterRequest registerRequest) {
        if(repo.existsByPhone(registerRequest.getPhone())){
            throw new RuntimeException("PhoneNumber already exists!!!");
        }
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());
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
    public UserResponseEntity<AuthResponse> loginWithPhoneService(LoginRequest loginRequest) {
        String phone = loginRequest.getPhone();
        String otp = otpService.generateAndSendOtp(phone);
        
        // Create auth response with OTP for testing
        AuthResponse authResponse = new AuthResponse(
                null,
                null,
                "Login initiated",
                null,
                phone,
                Role.CUSTOMER,
                otp  // Return OTP for testing in Postman
        );

        return new UserResponseEntity<>(
                "OTP sent to phone number. Use it to verify.",
                200,
                authResponse,
                LocalDateTime.now()
        );
    }

    @Override
    public UserResponseEntity<OtpResponse> sendOtpService(String phone) {
        String otp = otpService.generateAndSendOtp(phone);
        OtpResponse otpResponse = new OtpResponse(
                "OTP sent successfully",
                phone,
                true,
                otp
        );
        return new UserResponseEntity<>(
                "OTP sent successfully",
                200,
                otpResponse,
                LocalDateTime.now()
        );
    }

    @Override
    public UserResponseEntity<AuthResponse> verifyOtpService(String phone, String otp) {
        boolean isValid = otpService.verifyOtp(phone, otp);

        if (!isValid) {
            throw new RuntimeException("Invalid OTP");
        }

        User user = repo.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found for phone: " + phone));

        String token = jwtUtil.generateToken(user);
        AuthResponse authResponse = new AuthResponse(
                token,
                null,
                user.getName(),
                null,
                phone,
                user.getRole(),
                null
        );

        return new UserResponseEntity<>(
                "OTP verified successfully. JWT token issued.",
                200,
                authResponse,
                LocalDateTime.now()
        );
    }
}
