package com.bluethinkInc.authentication.authorization_service.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterEventDto {
    private String eventType; // USER_REGISTERED
    private String name;
    private String email;
    private String phone;
    private String message;
}
