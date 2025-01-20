package com.agyat.project.uber.uberApp.dto;

import com.agyat.project.uber.uberApp.entities.Ride;
import com.agyat.project.uber.uberApp.entities.Wallet;
import com.agyat.project.uber.uberApp.entities.enums.TransactionMethod;
import com.agyat.project.uber.uberApp.entities.enums.TransactionType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class WalletTransactionDto {
    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    private RideDto ride;

    private String transactionId;

    private WalletDto wallet;

    private LocalDateTime timeStamp;
}
