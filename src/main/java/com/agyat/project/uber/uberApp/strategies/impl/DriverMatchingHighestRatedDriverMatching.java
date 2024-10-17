package com.agyat.project.uber.uberApp.strategies.impl;

import com.agyat.project.uber.uberApp.dto.RideRequestDto;
import com.agyat.project.uber.uberApp.entities.Driver;
import com.agyat.project.uber.uberApp.entities.RideRequest;
import com.agyat.project.uber.uberApp.strategies.DriverMatchingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;


public class DriverMatchingHighestRatedDriverMatching implements DriverMatchingStrategy {
    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return List.of();
    }
}
