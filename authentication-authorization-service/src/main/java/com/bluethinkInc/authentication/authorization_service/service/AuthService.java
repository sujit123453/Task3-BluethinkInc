package com.bluethinkInc.authentication.authorization_service.service;


import com.bluethinkInc.authentication.authorization_service.dto.LoginRequest;
import com.bluethinkInc.authentication.authorization_service.dto.RegisterRequest;
import com.bluethinkInc.authentication.authorization_service.dto.UserResponseEntity;
import com.bluethinkInc.authentication.authorization_service.dto.AuthResponse;
import com.bluethinkInc.authentication.authorization_service.dto.OtpResponse;
import com.bluethinkInc.authentication.authorization_service.model.User;

public interface AuthService {

    UserResponseEntity<User> registerService(RegisterRequest registerRequest);

    UserResponseEntity<AuthResponse> loginWithPhoneService(LoginRequest loginRequest);

    UserResponseEntity<OtpResponse> sendOtpService(String phone);

    UserResponseEntity<AuthResponse> verifyOtpService(String phone, String otp);
}
