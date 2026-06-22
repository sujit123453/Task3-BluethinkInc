package com.bluethinkInc.loan_service.dto;

import com.bluethinkInc.loan_service.enums.LoanStatus;
import lombok.Data;

@Data
public class LoanApprovalRequest {
    private LoanStatus loanStatus;
    private String remarks;
}
