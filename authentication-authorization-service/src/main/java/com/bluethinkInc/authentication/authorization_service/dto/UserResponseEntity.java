package com.bluethinkInc.authentication.authorization_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseEntity<T> {
    private String message;
    private int status;
    private T data;
    private LocalDateTime timestamp;
}
