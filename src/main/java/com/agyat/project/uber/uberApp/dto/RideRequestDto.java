package com.agyat.project.uber.uberApp.dto;

import com.agyat.project.uber.uberApp.entities.enums.PaymentMethod;
import com.agyat.project.uber.uberApp.entities.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {
    private Long id;


    private PointDto pickUpLocation;
    private PointDto dropOffLocation;
    private PaymentMethod paymentMethod;

    private LocalDateTime requestedTime;

    private RiderDto rider;
    private Double fare;

    private RideRequestStatus rideRequestStatus;



}
