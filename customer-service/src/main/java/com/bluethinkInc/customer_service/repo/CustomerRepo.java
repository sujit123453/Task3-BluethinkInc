package com.bluethinkInc.customer_service.repo;

import com.bluethinkInc.customer_service.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhone(String phone);

    List<Customer> findByAccountStatus(String accountStatus);

    List<Customer> findByKycCompleted(Boolean kycCompleted);
}
