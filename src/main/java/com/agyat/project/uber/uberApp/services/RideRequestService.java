package com.agyat.project.uber.uberApp.services;

import com.agyat.project.uber.uberApp.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(long rideRequestId);

    void update(RideRequest rideRequest);
}
