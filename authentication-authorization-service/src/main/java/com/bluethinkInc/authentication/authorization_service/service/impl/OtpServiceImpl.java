package com.bluethinkInc.authentication.authorization_service.service.impl;

import com.bluethinkInc.authentication.authorization_service.service.OtpService;
import com.bluethinkInc.authentication.authorization_service.service.SmsService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpServiceImpl implements OtpService {

    private final RedisTemplate<String, String> redisTemplate;
    private final SmsService smsService;
    private static final long OTP_EXPIRY_MINUTES = 5;
    private static final String OTP_KEY_PREFIX = "otp:";

    public OtpServiceImpl(RedisTemplate<String, String> redisTemplate, SmsService smsService) {
        this.redisTemplate = redisTemplate;
        this.smsService = smsService;
    }

    @Override
    public String generateAndSendOtp(String phone) {
        String otp = generateOtp();
        String key = OTP_KEY_PREFIX + phone;
        
        redisTemplate.opsForValue().set(key, otp, OTP_EXPIRY_MINUTES, TimeUnit.MINUTES);
        
        String message = "Your OTP is: " + otp + ". Valid for " + OTP_EXPIRY_MINUTES + " minutes.";
        smsService.sendSms(phone, message);
        
        return otp;
    }

    @Override
    public boolean verifyOtp(String phone, String otp) {
        String key = OTP_KEY_PREFIX + phone;
        String storedOtp = redisTemplate.opsForValue().get(key);
        
        if (storedOtp == null) {
            throw new RuntimeException("OTP expired or not found");
        }
        
        if (storedOtp.equals(otp)) {
            redisTemplate.delete(key);
            return true;
        }
        
        throw new RuntimeException("Invalid OTP");
    }

    @Override
    public void deleteOtp(String phone) {
        String key = OTP_KEY_PREFIX + phone;
        redisTemplate.delete(key);
    }

    @Override
    public String getOtpForTesting(String phone) {
        String key = OTP_KEY_PREFIX + phone;
        return redisTemplate.opsForValue().get(key);
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
