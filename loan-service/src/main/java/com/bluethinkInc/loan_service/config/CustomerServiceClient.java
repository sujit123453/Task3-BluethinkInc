package com.bluethinkInc.loan_service.config;

import com.bluethinkInc.loan_service.dto.CustomerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerServiceClient {

    @GetMapping("/customer/internal/{id}")
    CustomerResponseDto getCustomerById(@PathVariable("id") Long id);
}
