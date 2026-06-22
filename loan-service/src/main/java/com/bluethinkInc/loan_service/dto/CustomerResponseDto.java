package com.bluethinkInc.loan_service.dto;

import lombok.Data;

@Data
public class CustomerResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String accountStatus;
    private Boolean kycCompleted;
}
