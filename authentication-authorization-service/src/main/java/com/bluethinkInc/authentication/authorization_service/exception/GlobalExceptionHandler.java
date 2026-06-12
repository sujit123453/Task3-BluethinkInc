package com.bluethinkInc.authentication.authorization_service.exception;

import com.bluethinkInc.authentication.authorization_service.dto.UserResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<UserResponseEntity<Object>> handleRuntime(RuntimeException ex) {
        UserResponseEntity<Object> body = new UserResponseEntity<>(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                null,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserResponseEntity<Object>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; "));
        UserResponseEntity<Object> body = new UserResponseEntity<>(
                message,
                HttpStatus.BAD_REQUEST.value(),
                null,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}

