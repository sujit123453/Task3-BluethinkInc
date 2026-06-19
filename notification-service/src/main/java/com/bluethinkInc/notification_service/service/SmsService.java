//package com.bluethinkInc.notification_service.service;
//
//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SmsService {
//
//    @Value("${twilio.account-sid}")
//    private String accountSid;
//
//    @Value("${twilio.auth-token}")
//    private String authToken;
//
//    @Value("${twilio.phone-number}")
//    private String fromNumber;
//
//    @PostConstruct
//    public void init() {
//        Twilio.init(accountSid, authToken);
//    }
//
//    public void sendSms(String toPhone, String message) {
//        try {
//            String formattedPhone = toPhone.startsWith("+") ? toPhone : "+91" + toPhone;
//
//            Message msg = Message.creator(
//                    new PhoneNumber(formattedPhone),
//                    new PhoneNumber(fromNumber),
//                    message
//            ).create();
//
//            System.out.println("SMS SID: " + msg.getSid());
//            System.out.println("SMS sent to " + formattedPhone);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
