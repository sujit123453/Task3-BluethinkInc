package com.bluethinkInc.transaction_service.service;

import com.bluethinkInc.transaction_service.dto.request.CreditRequestDto;
import com.bluethinkInc.transaction_service.dto.request.TransferRequestDto;
import com.bluethinkInc.transaction_service.dto.request.WithdrawRequestDto;
import com.bluethinkInc.transaction_service.dto.response.TransactionResponseDto;
import org.springframework.stereotype.Service;


public interface TransactionService {
    TransactionResponseDto depositAmountService(CreditRequestDto creditRequestDto);

    TransactionResponseDto withdrawAmountService(WithdrawRequestDto withdrawRequestDto);

    TransactionResponseDto transferAmountService(TransferRequestDto transferRequestDto);

    Object getTransactionHistoryService(String accountNumber);
}
