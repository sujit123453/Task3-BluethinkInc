package com.bluethinkInc.transaction_service.repository;

import com.bluethinkInc.transaction_service.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    boolean existsByTransactionReference(String transactionReference);
    List<Transaction> findByFromAccountNumberOrToAccountNumber(String fromAccount, String toAccount);
}
