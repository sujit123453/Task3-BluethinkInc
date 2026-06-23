package com.bluethinkInc.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEventDto {
    private String eventType;

    private Long accountId;
    private String accountNumber;
    private Long customerId;
    private String firstName;
    private String lastName;
    private String accountType;
    private String accountStatus;
    private Double balance;

}
