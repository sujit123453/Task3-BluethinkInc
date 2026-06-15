package com.bluethinkInc.customer_service.service;

import com.bluethinkInc.customer_service.dto.*;

import java.util.List;

public interface CustomerService {

    CustomerResponse createCustomer(CreateCustomerRequest request);

    CustomerResponse getCustomerById(Long id);

    CustomerResponse getCustomerByEmail(String email);

    CustomerResponse getCustomerByPhone(String phone);

    List<CustomerResponse> getAllCustomers();

    List<CustomerResponse> getCustomersByAccountStatus(String accountStatus);

    List<CustomerResponse> getCustomersByKycStatus(Boolean kycCompleted);

    CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request);

    CustomerResponse updateKycDetails(Long id, UpdateKycRequest request);

    void deleteCustomer(Long id);
}
