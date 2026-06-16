package com.bluethinkInc.customer_service.controller;

import com.bluethinkInc.customer_service.dto.*;
import com.bluethinkInc.customer_service.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Public endpoint (for direct client calls via API Gateway)
    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Internal endpoint: intended to be called by API Gateway after it validates JWT.
    @PostMapping("/internal/register")
    public ResponseEntity<CustomerResponse> createCustomerInternal(@Valid @RequestBody CreateCustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
//    @GetMapping("/internal/{id}")
//    public ResponseEntity<CustomerResponse> getCustomerDetails(@PathVariable Long id){
//        CustomerResponse response = customerService.getCustomerDetailsService(id);
//        return new ResponseEntity<>(response, HttpStatus.FOUND);
//    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'MANAGER', 'ACCOUNTANT', 'EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        CustomerResponse response = customerService.getCustomerById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'MANAGER', 'ACCOUNTANT', 'EMPLOYEE')")
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponse> getCustomerByEmail(@PathVariable String email) {
        CustomerResponse response = customerService.getCustomerByEmail(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'MANAGER', 'ACCOUNTANT', 'EMPLOYEE')")
    @GetMapping("/phone/{phone}")
    public ResponseEntity<CustomerResponse> getCustomerByPhone(@PathVariable String phone) {
        CustomerResponse response = customerService.getCustomerByPhone(phone);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'ACCOUNTANT')")
    @GetMapping("/all")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> response = customerService.getAllCustomers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'ACCOUNTANT')")
    @GetMapping("/status/{accountStatus}")
    public ResponseEntity<List<CustomerResponse>> getCustomersByAccountStatus(@PathVariable String accountStatus) {
        List<CustomerResponse> response = customerService.getCustomersByAccountStatus(accountStatus);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'ACCOUNTANT')")
    @GetMapping("/kyc/{kycCompleted}")
    public ResponseEntity<List<CustomerResponse>> getCustomersByKycStatus(@PathVariable Boolean kycCompleted) {
        List<CustomerResponse> response = customerService.getCustomersByKycStatus(kycCompleted);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'ACCOUNTANT', 'EMPLOYEE')")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @Valid @RequestBody UpdateCustomerRequest request) {
        CustomerResponse response = customerService.updateCustomer(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    @PutMapping("/{id}/kyc")
    public ResponseEntity<CustomerResponse> updateKycDetails(@PathVariable Long id, @Valid @RequestBody UpdateKycRequest request) {
        CustomerResponse response = customerService.updateKycDetails(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/internal/{id}")
    public ResponseEntity<CustomerResponse> getCustomerByIdInternal(@PathVariable Long id) {
        CustomerResponse response = customerService.getCustomerById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
