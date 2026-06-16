package com.bluethinkInc.account_service.dto;

import com.bluethinkInc.account_service.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatusDto {
    private String message;
    private String accountNumber;
    private AccountStatus accountStatus;
}
