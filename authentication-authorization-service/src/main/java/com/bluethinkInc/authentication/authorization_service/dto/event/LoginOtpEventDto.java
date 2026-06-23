package com.bluethinkInc.authentication.authorization_service.dto.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginOtpEventDto {
    private String eventType;    // LOGIN_OTP
    private Long userId;

    private String name;
    private String email;
    private String phone;

    private String otp;

    private String message;
}
