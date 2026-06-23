package com.bluethinkInc.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthEventDto {
    private String eventType;    // LOGIN_OTP
    private Long userId;

    private String name;
    private String email;
    private String phone;

    private String otp;

    private String message;
}
