package com.bluethinkInc.card_service.config;

import com.bluethinkInc.card_service.dto.clients.AccountResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-service")
public interface AccountServiceClient {

    @GetMapping("/account/internal/customer/{customerId}")
    AccountResponseDto getAccountByCustomerId(@PathVariable("customerId") Long customerId);
}
