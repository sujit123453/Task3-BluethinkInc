package com.bluethinkInc.authentication.authorization_service.service;


import com.bluethinkInc.authentication.authorization_service.dto.LoginRequest;
import com.bluethinkInc.authentication.authorization_service.dto.RegisterRequest;
import com.bluethinkInc.authentication.authorization_service.dto.UserResponseEntity;
import com.bluethinkInc.authentication.authorization_service.dto.AuthResponse;
import com.bluethinkInc.authentication.authorization_service.model.User;

public interface AuthService {

    UserResponseEntity<User> registerService(RegisterRequest registerRequest);

    UserResponseEntity<AuthResponse> loginService(LoginRequest loginRequest);
}
