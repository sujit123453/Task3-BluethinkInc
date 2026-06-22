package com.bluethinkInc.card_service.utils;


import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerateRandomNum {

    public String generateCardNumber(){
        Random random = new Random();
        long num = 100000000000L + (long) (random.nextDouble() * 900000000000L);
        return String.valueOf(num);
    }

    public String generateCardPin(){
        Random random = new Random();
        return String.valueOf(1000 + random.nextInt(9000));
    }

    public String generateCvv(){
        Random random = new Random();
        int cvv = 100 + random.nextInt(900);
        return String.valueOf(cvv);
    }
}
