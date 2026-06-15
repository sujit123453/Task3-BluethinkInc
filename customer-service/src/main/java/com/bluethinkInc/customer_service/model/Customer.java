package com.bluethinkInc.customer_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
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
