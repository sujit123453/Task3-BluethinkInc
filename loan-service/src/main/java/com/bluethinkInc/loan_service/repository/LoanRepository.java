package com.bluethinkInc.loan_service.repository;

import com.bluethinkInc.loan_service.model.Loan;
import com.bluethinkInc.loan_service.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByCustomerId(Long customerId);

    @Query("SELECT COUNT(l) FROM Loan l WHERE l.customerId = :customerId AND l.loanStatus = :status")
    long countByCustomerIdAndLoanStatus(@Param("customerId") Long customerId, @Param("status") LoanStatus status);
}
