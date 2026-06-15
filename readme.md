# Production-Level Banking System – Essential APIs and Their Descriptions

## 1. Authentication & Authorization Service

**Purpose:** Manages user authentication, OTP verification, JWT token generation, and authorization.

| Method | API                   | Description                                                |
| ------ | --------------------- | ---------------------------------------------------------- |
| POST   | `/auth/register`      | Registers a new user with phone/email and role.            |
| POST   | `/auth/send-otp`      | Generates and sends an OTP to the registered phone number. |
| POST   | `/auth/verify-otp`    | Verifies the OTP and returns a JWT access token.           |
| POST   | `/auth/refresh-token` | Generates a new access token using a refresh token.        |
| GET    | `/auth/profile`       | Returns details of the currently logged-in user.           |

### Flow

````
Register → Send OTP → Verify OTP → Generate JWT → Access Protected APIs
``

---

# 2. Customer Service

**Purpose:** Maintains customer profile and KYC information.

| Method | API                           | Description                                                     |
| ------ | ----------------------------- | --------------------------------------------------------------- |
| POST   | `/customers`                  | Creates a new customer profile after successful authentication. |
| GET    | `/customers/{customerId}`     | Retrieves customer details.                                     |
| PUT    | `/customers/{customerId}`     | Updates customer information such as address or phone.          |
| PUT    | `/customers/{customerId}/kyc` | Updates KYC verification details.                               |

### Flow

```
JWT Verified
      │
Create Customer
      │
Store Personal & KYC Details
```

---

# 3. Account Service

**Purpose:** Manages bank accounts and balances.

| Method | API                             | Description                      |
| ------ | ------------------------------- | -------------------------------- |
| POST   | `/accounts`                     | Opens a savings/current account. |
| GET    | `/accounts/{accountId}`         | Retrieves account information.   |
| GET    | `/accounts/balance/{accountId}` | Returns current account balance. |
| PUT    | `/accounts/block/{accountId}`   | Blocks or freezes an account.    |

### Flow

```
Customer Exists
      │
Open Account
      │
Generate Account Number(deposit some money)
      │
Initialize Balance
```

---

# 4. Transaction Service

**Purpose:** Handles money movement between accounts.

| Method | API                                 | Description                           |
| ------ | ----------------------------------- | ------------------------------------- |
| POST   | `/transactions/deposit`             | Deposits money into an account.       |
| POST   | `/transactions/withdraw`            | Withdraws money from an account.      |
| POST   | `/transactions/transfer`            | Transfers money between two accounts. |
| GET    | `/transactions/history/{accountId}` | Returns transaction history.          |

### Flow

```
Validate Account
      │
Check Balance
      │
Debit/Credit
      │
Save Transaction
```

---

# 5. Payment Service(Optional)

**Purpose:** Processes utility bills, merchant payments, and UPI transactions.

| Method | API                              | Description                               |
| ------ | -------------------------------- | ----------------------------------------- |
| POST   | `/payments/upi`                  | Performs a UPI payment.                   |
| POST   | `/payments/bill`                 | Pays electricity, water, gas, etc. bills. |
| POST   | `/payments/merchant`             | Pays a merchant or online store.          |
| GET    | `/payments/history/{customerId}` | Retrieves payment history.                |

### Flow

```
Payment Request
      │
Validate Account
      │
Debit Amount
      │
Complete Payment
```

---

# 6. Loan Service

**Purpose:** Handles loan applications and repayments.

| Method | API                       | Description                 |
| ------ | ------------------------- | --------------------------- |
| POST   | `/loans/apply`            | Applies for a new loan.     |
| GET    | `/loans/{loanId}`         | Retrieves loan details.     |
| PUT    | `/loans/approve/{loanId}` | Approves or rejects a loan. |
| POST   | `/loans/pay-emi`          | Pays the monthly EMI.       |

### Flow

```
Apply Loan(According to the profiling of their account)
     │
Verify Customer
     │
Approve
     │
Generate EMI
```

---

# 7. Card Service

**Purpose:** Manages debit and credit cards.

| Method | API                     | Description                     |
| ------ | ----------------------- | ------------------------------- |
| POST   | `/cards/issue`          | Issues a new debit/credit card. |
| GET    | `/cards/{cardId}`       | Retrieves card details.         |
| PUT    | `/cards/block/{cardId}` | Blocks a lost or stolen card.   |
| PUT    | `/cards/change-pin`     | Changes the card PIN.           |

### Flow

```
Request Card
      │
Generate Card
      │
Assign PIN
      │
Activate
```

---

# 8. Notification Service

**Purpose:** Sends OTPs and transaction notifications.

| Method | API                                   | Description                     |
| ------ | ------------------------------------- | ------------------------------- |
| POST   | `/notifications/otp`                  | Sends login OTP.                |
| POST   | `/notifications/sms`                  | Sends SMS notification.         |
| POST   | `/notifications/email`                | Sends email notification.       |
| GET    | `/notifications/history/{customerId}` | Retrieves notification history. |

### Flow

```
Receive Event
      │
Send SMS/Email
      │
Store Status
```

---

# 9. Fraud Detection Service

**Purpose:** Detects suspicious banking activities.

| Method | API                                | Description                                 |
| ------ | ---------------------------------- | ------------------------------------------- |
| POST   | `/fraud/check`                     | Checks whether a transaction is suspicious. |
| PUT    | `/fraud/block-account/{accountId}` | Blocks an account if fraud is detected.     |
| GET    | `/fraud/risk-score/{customerId}`   | Returns the customer's risk score.          |

### Flow

```
Transaction
      │
Analyze
      │
High Risk?
 ├─ Yes → Block
 └─ No → Continue
```

---

# 10. Audit & Logging Service

**Purpose:** Stores all important system activities for compliance and debugging.

| Method | API                            | Description                       |
| ------ | ------------------------------ | --------------------------------- |
| POST   | `/audit/log`                   | Stores an audit record.           |
| GET    | `/audit/customer/{customerId}` | Retrieves customer activity logs. |
| GET    | `/audit/activity`              | Retrieves all system audit logs.  |

### Flow

```
Any Business Event
        │
Create Log
        │
Store Audit Record
```

---

# Overall Banking Execution Flow

## Customer Onboarding

```
Register
    │
    ▼
Authentication Service
    │
Send OTP
    │
Verify OTP
    │
Generate JWT
    │
    ▼
Customer Service
(Create Profile)
    │
    ▼
Account Service
(Open Savings Account)
    │
    ▼
Notification Service
(Send Welcome SMS)
```

## Fund Transfer

```
JWT Verified
      │
      ▼
Transaction Service
      │
Check Balance
      │
Debit Sender
      │
Credit Receiver
      │
      ├──► Fraud Detection
      ├──► Notification
      └──► Audit Logging
```

## Loan Processing

```
Apply Loan
     │
     ▼
Loan Service
     │
Verify Customer
     │
Fraud Check
     │
Approve/Reject
     │
Generate EMI
     │
Notify Customer
```

This streamlined set of APIs covers the core banking operations while keeping each microservice focused on a single business responsibility.
