package com.bluethinkInc.authentication.authorization_service.dto;

import com.bluethinkInc.authentication.authorization_service.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
    private Long id;
    private String name;
    private String email;
    private Role role;
}

