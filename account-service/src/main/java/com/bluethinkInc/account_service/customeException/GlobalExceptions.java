package com.bluethinkInc.account_service.customeException;

import com.bluethinkInc.account_service.customeException.exceptions.CustomerIdNotFoundException;
import com.bluethinkInc.account_service.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptions {
    @ExceptionHandler(CustomerIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> customerIdNotFoundException(CustomerIdNotFoundException ex){
        ErrorResponse response = new ErrorResponse(
                "Give id is not found, Please do register again",
                404,
                LocalDateTime.now()
        );
        return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
