package com.bluethinkInc.loan_service.controller;

import com.bluethinkInc.loan_service.dto.*;
import com.bluethinkInc.loan_service.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/apply")
    public ResponseEntity<LoanResponse> applyLoan(@RequestBody LoanRequest request) {
        return new ResponseEntity<>(loanService.applyLoan(request), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN','MANAGER','ACCOUNTANT')")
    @GetMapping("/{loanId}")
    public ResponseEntity<LoanResponse> getLoanById(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.getLoanById(loanId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/approve/{loanId}")
    public ResponseEntity<LoanResponse> approveLoan(@PathVariable Long loanId,
                                                     @RequestBody LoanApprovalRequest request) {
        return ResponseEntity.ok(loanService.approveLoan(loanId, request));
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN','ACCOUNTANT')")
    @PostMapping("/pay-emi")
    public ResponseEntity<LoanResponse> payEmi(@RequestBody EmiPaymentRequest request) {
        return ResponseEntity.ok(loanService.payEmi(request));
    }
}
