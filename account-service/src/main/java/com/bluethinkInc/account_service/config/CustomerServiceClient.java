package com.bluethinkInc.account_service.config;

import com.bluethinkInc.account_service.dto.feignClient.CustomerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerServiceClient {

    @GetMapping("/customer/internal/{id}")
    CustomerResponseDto getCustomerById(@PathVariable("id") Long id);
}
