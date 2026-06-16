package com.bluethinkInc.account_service.utils;

import com.bluethinkInc.account_service.enums.AccountType;
import com.bluethinkInc.account_service.repo.AccountRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;


@Slf4j
@Service
public class AccountNumberGenerator {
    private final AccountRepo accountRepo;

    public AccountNumberGenerator(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public String generateAccountNumber(AccountType accountType) {
        String prefix = accountType == accountType.SAVINGS ? "SA" : "CA";
        String date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyMMdd"));
        String accountNumber;
        do {
            int random = ThreadLocalRandom.current()
                    .nextInt(100000, 1000000);
            accountNumber = prefix + date + random;
        }while(accountRepo.existsByAccountNumber(accountNumber));
        log.info("Generated account number: {}",accountNumber);
        return accountNumber;
    }
}