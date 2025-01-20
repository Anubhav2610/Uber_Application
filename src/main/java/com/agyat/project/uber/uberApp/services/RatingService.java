package com.agyat.project.uber.uberApp.services;

import com.agyat.project.uber.uberApp.dto.DriverDto;
import com.agyat.project.uber.uberApp.dto.RiderDto;
import com.agyat.project.uber.uberApp.entities.Driver;
import com.agyat.project.uber.uberApp.entities.Rating;
import com.agyat.project.uber.uberApp.entities.Ride;


public interface RatingService {

    RiderDto rateRider(Ride ride , Integer rating);
    DriverDto rateDriver(Ride ride , Integer rating);
    void createNewRating(Ride ride);
}
