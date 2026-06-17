package com.bluethinkInc.transaction_service.customeException;

import com.bluethinkInc.transaction_service.customeException.exceptions.AccountNotFoundException;
import com.bluethinkInc.transaction_service.customeException.exceptions.InsufficientBalanceException;
import com.bluethinkInc.transaction_service.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFound(AccountNotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage(), 404, LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalance(InsufficientBalanceException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage(), 400, LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorResponse> handleSecurityException(SecurityException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage(), 403, LocalDateTime.now()), HttpStatus.FORBIDDEN);
    }
}
