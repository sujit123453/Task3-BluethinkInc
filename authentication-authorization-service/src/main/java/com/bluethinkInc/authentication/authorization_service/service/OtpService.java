package com.bluethinkInc.authentication.authorization_service.service;

public interface OtpService {

    String generateAndSendOtp(String phone);

    boolean verifyOtp(String phone, String otp);

    void deleteOtp(String phone);

    String getOtpForTesting(String phone);
}
