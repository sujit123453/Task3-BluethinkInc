package com.bluethinkInc.customer_service.customException.exception;

public class InvalidAmountException extends RuntimeException{
    public InvalidAmountException(String message){
        super(message);
    }
}
