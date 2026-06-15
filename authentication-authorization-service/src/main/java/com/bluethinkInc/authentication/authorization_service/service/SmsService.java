package com.bluethinkInc.authentication.authorization_service.service;

public interface SmsService {

    void sendSms(String phone, String message);
}
