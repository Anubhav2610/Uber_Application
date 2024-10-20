package com.agyat.project.uber.uberApp.services;

import com.agyat.project.uber.uberApp.dto.DriverDto;
import com.agyat.project.uber.uberApp.dto.RideDto;
import com.agyat.project.uber.uberApp.dto.RideRequestDto;
import com.agyat.project.uber.uberApp.dto.RiderDto;
import com.agyat.project.uber.uberApp.entities.Rider;
import com.agyat.project.uber.uberApp.entities.User;

import java.util.List;

public interface RiderService {
    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long  rideId);

    DriverDto rateRider(Long rideId , Integer rating);

    RiderDto getMyProfile();

    List<RideDto> getAllMyRides();

    Rider createNewRider(User savedUser);

    Rider getCurrentRider();
}
