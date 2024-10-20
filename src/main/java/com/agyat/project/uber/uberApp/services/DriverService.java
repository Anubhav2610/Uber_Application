package com.agyat.project.uber.uberApp.services;

import com.agyat.project.uber.uberApp.dto.DriverDto;
import com.agyat.project.uber.uberApp.dto.RideDto;
import com.agyat.project.uber.uberApp.entities.Driver;

import java.util.List;

public interface DriverService {
    RideDto acceptRide(Long rideRequestId);

    RideDto cancelRide(Long  rideId);

    RideDto startRide(Long rideId , String otp);

    RideDto endRide(Long rideId);

    RideDto rateRider(Long rideId , Integer rating);

    DriverDto getMyProfile();

    List<RideDto> getAllMyRides();

    Driver getCurrentDriver();
}
