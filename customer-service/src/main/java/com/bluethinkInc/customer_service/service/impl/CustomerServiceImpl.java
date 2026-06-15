package com.bluethinkInc.customer_service.service.impl;

import com.bluethinkInc.customer_service.customException.exception.AlreadyExistException;
import com.bluethinkInc.customer_service.customException.exception.ResourceNotFoundException;
import com.bluethinkInc.customer_service.dto.*;
import com.bluethinkInc.customer_service.model.Customer;
import com.bluethinkInc.customer_service.repo.CustomerRepo;
import com.bluethinkInc.customer_service.service.CustomerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        if (customerRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new AlreadyExistException("Email already registered");
        }

        if (customerRepo.findByPhone(request.getPhone()).isPresent()) {
            throw new AlreadyExistException("Phone number already registered");
        }

        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setZipCode(request.getZipCode());
        customer.setCountry(request.getCountry());
        customer.setIdProofType(request.getIdProofType());
        customer.setIdProofNumber(request.getIdProofNumber());
        customer.setOccupation(request.getOccupation());
        customer.setIncome(request.getIncome());
        customer.setAccountStatus("ACTIVE");
        customer.setKycCompleted(false);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        Customer savedCustomer = customerRepo.save(customer);
        return mapToResponse(savedCustomer);
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
        return mapToResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerByEmail(String email) {
        Customer customer = customerRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + email));
        return mapToResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerByPhone(String phone) {
        Customer customer = customerRepo.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with phone: " + phone));
        return mapToResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepo.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponse> getCustomersByAccountStatus(String accountStatus) {
        return customerRepo.findByAccountStatus(accountStatus).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponse> getCustomersByKycStatus(Boolean kycCompleted) {
        return customerRepo.findByKycCompleted(kycCompleted).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        if (!customer.getPhone().equals(request.getPhone()) && 
            customerRepo.findByPhone(request.getPhone()).isPresent()) {
            throw new RuntimeException("Phone number already registered");
        }

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setZipCode(request.getZipCode());
        customer.setCountry(request.getCountry());
        customer.setOccupation(request.getOccupation());
        customer.setIncome(request.getIncome());
        customer.setUpdatedAt(LocalDateTime.now());

        Customer updatedCustomer = customerRepo.save(customer);
        return mapToResponse(updatedCustomer);
    }

    @Override
    public CustomerResponse updateKycDetails(Long id, UpdateKycRequest request) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        customer.setIdProofType(request.getIdProofType());
        customer.setIdProofNumber(request.getIdProofNumber());
        customer.setKycCompleted(request.getKycCompleted());
        customer.setUpdatedAt(LocalDateTime.now());

        Customer updatedCustomer = customerRepo.save(customer);
        return mapToResponse(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        customer.setAccountStatus("INACTIVE");
        customerRepo.save(customer);
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getDateOfBirth(),
                customer.getAddress(),
                customer.getCity(),
                customer.getState(),
                customer.getZipCode(),
                customer.getCountry(),
                customer.getIdProofType(),
                customer.getIdProofNumber(),
                customer.getOccupation(),
                customer.getIncome(),
                customer.getAccountStatus(),
                customer.getKycCompleted(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }
}
