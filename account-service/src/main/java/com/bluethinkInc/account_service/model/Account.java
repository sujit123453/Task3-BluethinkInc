package com.bluethinkInc.account_service.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;

    private Long customerId;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    private BigDecimal balance;

    private String currency;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime activatedAt;

    private LocalDateTime closedAt;

    private boolean kycVerified;

    private boolean act