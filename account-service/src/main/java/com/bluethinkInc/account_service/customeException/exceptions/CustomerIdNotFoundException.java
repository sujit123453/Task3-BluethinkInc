package com.bluethinkInc.account_service.customeException.exceptions;

public class CustomerIdNotFoundException extends RuntimeException {
    public CustomerIdNotFoundException(String message) {
        super(message);
    }
}