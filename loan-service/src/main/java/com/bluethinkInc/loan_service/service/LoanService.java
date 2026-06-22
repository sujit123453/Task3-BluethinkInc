package com.bluethinkInc.loan_service.service;

import com.bluethinkInc.loan_service.dto.*;

public interface LoanService {
    LoanResponse applyLoan(LoanRequest request);
    LoanResponse getLoanById(Long loanId);
    LoanResponse approveLoan(Long loanId, LoanApprovalRequest request);
    LoanResponse payEmi(EmiPaymentRequest request);
}
