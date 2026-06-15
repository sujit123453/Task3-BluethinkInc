package com.bluethinkInc.authentication.authorization_service.service.impl;

import com.bluethinkInc.authentication.authorization_service.service.SmsService;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {

    @Override
    public void sendSms(String phone, String message) {
        // Mock SMS sending - In production, integrate with AWS SNS, Twilio, or similar
        System.out.println("[SMS_SENT] Phone: " + phone + " | Message: " + message);
    }
}
