package com.bluethinkInc.card_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(String message, T data, int status) {
        return new ApiResponse<>(status, message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> error(String message, int status) {
        return new ApiResponse<>(status, message, null, LocalDateTime.now());
    }
}
