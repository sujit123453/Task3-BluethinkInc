package com.bluethinkInc.transaction_service.config;

import com.bluethinkInc.transaction_service.dto.response.CustomerDetailsResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerServiceClient {

    @GetMapping("/customer/phone/{phone}")
    CustomerDetailsResponseDto getCustomerByPhone(@PathVariable("phone") String phone);
}
