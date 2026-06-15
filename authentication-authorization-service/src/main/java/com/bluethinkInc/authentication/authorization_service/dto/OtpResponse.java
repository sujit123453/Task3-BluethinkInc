package com.bluethinkInc.authentication.authorization_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpResponse {

    private String message;

    private String phone;

    private Boolean success;

    private String otp;
}
