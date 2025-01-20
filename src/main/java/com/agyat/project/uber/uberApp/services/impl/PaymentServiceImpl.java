package com.agyat.project.uber.uberApp.services.impl;

import com.agyat.project.uber.uberApp.entities.Payment;
import com.agyat.project.uber.uberApp.entities.Ride;
import com.agyat.project.uber.uberApp.entities.enums.PaymentStatus;
import com.agyat.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.agyat.project.uber.uberApp.repositories.PaymentRepository;
import com.agyat.project.uber.uberApp.services.PaymentService;
import com.agyat.project.uber.uberApp.services.RideService;
import com.agyat.project.uber.uberApp.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private  final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;

    @Override
    public void ProcessPayment(Ride ride) {
        Payment payment = paymentRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Payment Not Found for Ride With Id "+ ride.getId()));
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
    }

    @Override
    public Payment createNewPayment(Ride ride) {
        Payment payment = Payment.builder()
                .paymentMethod(ride.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .amount(ride.getFare())
                .ride(ride)
                .build();
        return paymentRepository.save(payment);

    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus) {
        payment.setPaymentStatus(paymentStatus);
        paymentRepository.save(payment);
    }


}
