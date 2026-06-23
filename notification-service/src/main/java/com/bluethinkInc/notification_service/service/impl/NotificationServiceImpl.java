package com.bluethinkInc.notification_service.service.impl;

import com.bluethinkInc.notification_service.dto.AccountEventDto;
import com.bluethinkInc.notification_service.dto.AuthEventDto;
import com.bluethinkInc.notification_service.dto.TransactionEventDto;
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
}
