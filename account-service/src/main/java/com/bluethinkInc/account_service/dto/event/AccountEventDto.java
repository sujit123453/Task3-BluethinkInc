package com.bluethinkInc.account_service.dto.event;

import lombok.Data;

@Data
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
