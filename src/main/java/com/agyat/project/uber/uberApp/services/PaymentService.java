package com.agyat.project.uber.uberApp.services;

import com.agyat.project.uber.uberApp.entities.Payment;
import com.agyat.project.uber.uberApp.entities.Ride;
import com.agyat.project.uber.uberApp.entities.enums.PaymentStatus;

public interface PaymentService {
    void ProcessPayment(Ride  ride);

    Payment createNewPayment(Ride ride);

    void updatePaymentStatus(Payment payment , PaymentStatus paymentStatus);

}
