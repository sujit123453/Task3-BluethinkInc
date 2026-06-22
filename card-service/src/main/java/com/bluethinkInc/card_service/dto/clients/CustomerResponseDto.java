package com.bluethinkInc.card_service.dto.clients;

import lombok.Data;

@Data
public class CustomerResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String accountStatus;
    private Boolean kycCompleted;
}
