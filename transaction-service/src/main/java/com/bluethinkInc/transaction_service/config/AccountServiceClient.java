package com.bluethinkInc.transaction_service.config;

import com.bluethinkInc.transaction_service.dto.response.AccountDetailsResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "account-service")
public interface AccountServiceClient {

    @GetMapping("/account/{accountNumber}")
    AccountDetailsResponseDto getAccountDetails(@PathVariable("accountNumber") String accountNumber);

    @PutMapping("/account/internal/update-balance/{accountNumber}")
    AccountDetailsResponseDto updateBalance(@PathVariable("accountNumber") String accountNumber,
                                            @RequestParam("newBalance") BigDecimal newBalance);
}
