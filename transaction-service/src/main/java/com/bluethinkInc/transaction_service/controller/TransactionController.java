package com.bluethinkInc.transaction_service.controller;

import com.bluethinkInc.transaction_service.dto.request.CreditRequestDto;
import com.bluethinkInc.transaction_service.dto.request.TransferRequestDto;
import com.bluethinkInc.transaction_service.dto.request.WithdrawRequestDto;
import com.bluethinkInc.transaction_service.dto.response.TransactionResponseDto;
import com.bluethinkInc.transaction_service.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ACCOUNTANT')")
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDto> depositAmountController(@RequestBody CreditRequestDto creditRequestDto){
        TransactionResponseDto response = transactionService.depositAmountService(creditRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ACCOUNTANT','CUSTOMER')")
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponseDto> withdrawAmountController(@RequestBody WithdrawRequestDto withdrawRequestDto){
        TransactionResponseDto response = transactionService.withdrawAmountService(withdrawRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER', 'ACCOUNTANT', 'CUSTOMER')")
    @PostMapping("/transfer-amount")
    public ResponseEntity<TransactionResponseDto> transferAmountController(@RequestBody TransferRequestDto transferRequestDto){
        TransactionResponseDto response = transactionService.transferAmountService(transferRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER', 'ACCOUNTANT', 'CUSTOMER')")
    @GetMapping("/history/{accountNumber}")
    public ResponseEntity<Object> getTransactionHistoryController(@PathVariable String accountNumber){
        Object response = transactionService.getTransactionHistoryService(accountNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
