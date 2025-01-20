package com.agyat.project.uber.uberApp.strategies.impl;

import com.agyat.project.uber.uberApp.entities.Driver;
import com.agyat.project.uber.uberApp.entities.Payment;
import com.agyat.project.uber.uberApp.entities.enums.PaymentStatus;
import com.agyat.project.uber.uberApp.entities.enums.TransactionMethod;
import com.agyat.project.uber.uberApp.repositories.PaymentRepository;
import com.agyat.project.uber.uberApp.services.PaymentService;
import com.agyat.project.uber.uberApp.services.WalletService;
import com.agyat.project.uber.uberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {
    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();

        double platformFees = payment.getAmount() * PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser() , platformFees , null , payment.getRide() , TransactionMethod.RIDE );

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
