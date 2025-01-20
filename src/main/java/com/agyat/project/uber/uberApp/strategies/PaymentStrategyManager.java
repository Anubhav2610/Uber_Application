package com.agyat.project.uber.uberApp.strategies;

import com.agyat.project.uber.uberApp.entities.enums.PaymentMethod;
import com.agyat.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.agyat.project.uber.uberApp.strategies.impl.CashPaymentStrategy;
import com.agyat.project.uber.uberApp.strategies.impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {

    private final WalletPaymentStrategy walletPaymentStrategy;
    private  final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
        return switch (paymentMethod){
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
        };
    }
}
