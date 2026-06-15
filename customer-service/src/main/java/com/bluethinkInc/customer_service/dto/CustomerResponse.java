package com.bluethinkInc.customer_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private LocalDate dateOfBirth;

    private String address;

    private String city;

    private String state;

    private String zipCode;

    private String country;

    private String idProofType;

    private String idProofNumber;

    private String occupation;

    private Double income;

    private String accountStatus;

    private Boolean kycCompleted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
