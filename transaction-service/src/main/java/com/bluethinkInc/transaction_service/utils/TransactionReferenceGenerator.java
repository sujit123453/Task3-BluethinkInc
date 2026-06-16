package com.bluethinkInc.transaction_service.utils;


import com.bluethinkInc.transaction_service.repository.TransactionRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TransactionReferenceGenerator {
    private final TransactionRepo transactionRepo;
    public TransactionReferenceGenerator(TransactionRepo transactionRepo){
        this.transactionRepo = transactionRepo;
    }
    public  String generateTransactionReference(){
        String prefix = "TRX";
        String date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyMMdd"));
        String  transactionReference;
        do {
            int random = ThreadLocalRandom.current()
                    .nextInt(100000, 1000000);
            transactionReference = prefix + date + random;
        }while (transactionRepo.existsByTransactionReference(transactionReference));
        return transactionReference;
    }
}
