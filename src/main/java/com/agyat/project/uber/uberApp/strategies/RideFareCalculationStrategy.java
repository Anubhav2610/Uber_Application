package com.agyat.project.uber.uberApp.strategies;


import com.agyat.project.uber.uberApp.entities.RideRequest;

public interface RideFareCalculationStrategy {
    double RIDE_FARE_MULTIPLIER = 10;

    double calculateFare(RideRequest rideRequest);
}
