package com.bluethinkInc.notification_service.service.impl;

import com.bluethinkInc.notification_service.dto.*;
import com.bluethinkInc.notification_service.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void handleAccountCreated(AccountEventDto event) {

        String smsMessage =
                "Dear " + event.getFirstName() +
                        ", your account ending with "
                        + event.getAccountNumber()
                        .substring(event.getAccountNumber().length()-4)
                        + " has been created successfully.";

        log.info(smsMessage);
    }

    @Override
    public void handleStatusChanged(AccountEventDto event) {

        String smsMessage =
                "Your account status has changed to "
                        + event.getAccountStatus();

        log.info(smsMessage);
    }

    @Override
    public void handleBalanceUpdated(AccountEventDto event) {

        String smsMessage =
                "Available balance : ₹" + event.getBalance();

        log.info(smsMessage);
    }
    @Override
    public void handleRegisterEvent(AuthEventDto event) {

        String smsMessage =
                "Dear " + event.getName() +
                        ", welcome to Our Bank. Your registration has been completed successfully.";

        // smsService.sendSms(event.getPhone(), smsMessage);

        log.info("Registration SMS sent to {} : {}", event.getPhone(), smsMessage);
    }

    @Override
    public void handleLoginOtpEvent(AuthEventDto event) {

        String smsMessage =
                "Dear " + event.getName() +
                        ", your OTP for login is " + event.getOtp() +
                        ". It is valid for 5 minutes. Please do not share it with anyone.";

        // smsService.sendSms(event.getPhone(), smsMessage);

        log.info("OTP SMS sent to {} : {}", event.getPhone(), smsMessage);
    }

    @Override
    public void handleTransactionDepositEvent(TransactionEventDto event) {

        String smsMessage =
                "Dear " + event.getCustomerName() +
                        ", your account ending with "
                        + event.getAccountNumber()
                        .substring(event.getAccountNumber().length() - 4)
                        + " has been credited with ₹" + event.getAmount() +
                        ". Ref No: " + event.getTransactionReference() +
                        ". Status: " + event.getTransactionStatus();

        // smsService.sendSms(event.getPhone(), smsMessage);

        log.info("Deposit SMS sent to {} : {}", event.getPhone(), smsMessage);
    }

    @Override
    public void handleTransactionWithdrawEvent(TransactionEventDto event) {

        String smsMessage =
                "Dear " + event.getCustomerName() +
                        ", ₹" + event.getAmount() +
                        " has been debited from your account ending with "
                        + event.getAccountNumber()
                        .substring(event.getAccountNumber().length() - 4)
                        + ". Ref No: " + event.getTransactionReference() +
                        ". Status: " + event.getTransactionStatus();

        // smsService.sendSms(event.getPhone(), smsMessage);

        log.info("Withdrawal SMS sent to {} : {}", event.getPhone(), smsMessage);
    }

    @Override
    public void handleTransactionTransferEvent(TransactionEventDto event) {

        String smsMessage =
                "Dear " + event.getCustomerName() +
                        ", ₹" + event.getAmount() +
                        " has been transferred successfully from your account ending with "
                        + event.getAccountNumber()
                        .substring(event.getAccountNumber().length() - 4)
                        + ". Ref No: " + event.getTransactionReference() +
                        ". Status: " + event.getTransactionStatus();

        // smsService.sendSms(event.getPhone(), smsMessage);

        log.info("Transfer SMS sent to {} : {}", event.getPhone(), smsMessage);
    }

    @Override
    public void handleLoanAppliedEvent(LoanEventDto event) {

        String subject = "Loan Application Received";

        String body = "Dear " + event.getCustomerName() + ",\n\n"
                + "Your loan application has been received successfully.\n"
                + "Loan Amount: ₹" + event.getAmount() + "\n"
                + "Tenure: " + event.getTenureMonths() + " months\n"
                + "Interest Rate: " + event.getInterestRate() + "%\n"
                + "Status: " + event.getLoanStatus() + "\n\n"
                + "Thank you for banking with us.";

//        emailService.sendEmail(event.getEmail(), subject, body);
//        smsService.sendSms(event.getPhone(), event.getMessage());
    }

    @Override
    public void handleLoanApprovedEvent(LoanEventDto event) {

        String subject = "Loan Approved";

        String body = "Dear " + event.getCustomerName() + ",\n\n"
                + "Congratulations! Your loan has been approved.\n"
                + "Loan ID: " + event.getLoanId() + "\n"
                + "Amount: ₹" + event.getAmount() + "\n"
                + "Tenure: " + event.getTenureMonths() + " months\n"
                + "Interest Rate: " + event.getInterestRate() + "%\n"
                + "Status: " + event.getLoanStatus() + "\n\n"
                + "Thank you for banking with us.";

//        emailService.sendEmail(event.getEmail(), subject, body);
//        smsService.sendSms(event.getPhone(), event.getMessage());
    }

    @Override
    public void handleEmiPaidEvent(LoanEventDto event) {

        String subject = "EMI Payment Successful";

        String body = "Dear " + event.getCustomerName() + ",\n\n"
                + "Your EMI payment has been received successfully.\n"
                + "Loan ID: " + event.getLoanId() + "\n"
                + "Outstanding Loan Amount: ₹" + event.getAmount() + "\n"
                + "Status: " + event.getLoanStatus() + "\n\n"
                + "Thank you for banking with us.";

//        emailService.sendEmail(event.getEmail(), subject, body);
//        smsService.sendSms(event.getPhone(), event.getMessage());
    }

    @Override
    public void handleCardIssueEvent(CardEventDto event) {

        String smsMessage =
                "Dear " + event.getCustomerName() +
                        ", your " + event.getCardType() +
                        " card ending with "
                        + event.getCardNumber()
                        .substring(event.getCardNumber().length() - 4)
                        + " has been issued successfully. Status: "
                        + event.getStatus();

        log.info("Card Issue Notification sent to {} : {}",
                event.getMobileNumber(), smsMessage);
    }

    @Override
    public void handleBlockCardEvent(CardEventDto event) {

        String smsMessage =
                "Dear " + event.getCustomerName() +
                        ", your " + event.getCardType() +
                        " card ending with "
                        + event.getCardNumber()
                        .substring(event.getCardNumber().length() - 4)
                        + " has been blocked successfully.";

        log.info("Card Block Notification sent to {} : {}",
                event.getMobileNumber(), smsMessage);
    }

    @Override
    public void handleCardPinChangeEvent(CardEventDto event) {

        String smsMessage =
                "Dear " + event.getCustomerName() +
                        ", PIN for your " + event.getCardType() +
                        " card ending with "
                        + event.getCardNumber()
                        .substring(event.getCardNumber().length() - 4)
                        + " has been changed successfully.";

        log.info("Card PIN Change Notification sent to {} : {}",
                event.getMobileNumber(), smsMessage);
    }
}
