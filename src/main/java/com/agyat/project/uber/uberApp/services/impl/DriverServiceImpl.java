package com.agyat.project.uber.uberApp.services.impl;

import com.agyat.project.uber.uberApp.dto.DriverDto;
import com.agyat.project.uber.uberApp.dto.RideDto;
import com.agyat.project.uber.uberApp.dto.RiderDto;
import com.agyat.project.uber.uberApp.entities.Driver;
import com.agyat.project.uber.uberApp.entities.Ride;
import com.agyat.project.uber.uberApp.entities.RideRequest;
import com.agyat.project.uber.uberApp.entities.User;
import com.agyat.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.agyat.project.uber.uberApp.entities.enums.RideStatus;
import com.agyat.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.agyat.project.uber.uberApp.repositories.DriverRepository;
import com.agyat.project.uber.uberApp.services.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.DeclareError;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;
    private final RatingService ratingService;

    @Override
    public RideDto acceptRide(Long rideRequestId) {
        RideRequest rideRequest =  rideRequestService.findRideRequestById(rideRequestId);

        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("Ride Request can not be accepted status is "+ rideRequest.getRideRequestStatus());
        }

        Driver currentDriver = getCurrentDriver();
        if(!currentDriver.getAvailable()){
            throw new RuntimeException("Driver cannot accept ride due to unavailability");
        }

        Driver savedDriver = updateDriveAvailabilty(currentDriver , false);

        Ride ride = rideService.createNewRide(rideRequest , savedDriver);
        return modelMapper.map(ride  , RideDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot cancel the ride");
        }
        if(!ride.getRidestatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride Cannot be cancelled , invalid status : "+ ride.getRidestatus());
        }
        rideService.updateRideStatus(ride ,RideStatus.CANCELLED);

        Driver savedDriver = updateDriveAvailabilty(driver , true);


        return modelMapper.map(ride , RideDto.class);
    }

    @Override
    public RideDto startRide(Long rideId , String otp) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver Can not Start a ride as he has not accepted ut earlier");
        }

        if(!ride.getRidestatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride Status is not CONFIRMED  hence can not be started . status : "+ ride.getRidestatus());
        }
        if(!otp.equals(ride.getOtp())){
            throw new RuntimeException("Otp is not valid , otp :"+ otp);
        }

        ride.setStartedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride , RideStatus.ONGOING);

        paymentService.createNewPayment(ride);

        return modelMapper.map(savedRide , RideDto.class);
    }

    @Override
    public RideDto endRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);

        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver Can not End a ride as he has not accepted ut earlier");
        }

        if(!ride.getRidestatus().equals(RideStatus.ONGOING)){
            throw new RuntimeException("Ride Status is not ONGOING  hence can not be started . status : "+ ride.getRidestatus());
        }

        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride , RideStatus.ENDED);

        updateDriveAvailabilty(driver , true);

        paymentService.ProcessPayment(ride);

        return modelMapper.map(savedRide , RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);

        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver is not owner of this ride");
        }

        if(!ride.getRidestatus().equals(RideStatus.ENDED)){

            throw new RuntimeException("Ride Status is not ENDED  hence can not rate . status : "+ ride.getRidestatus());
        }

        return ratingService.rateRider(ride , rating);
    }

    @Override
    public DriverDto getMyProfile() {
        Driver driver = getCurrentDriver();
        return modelMapper.map(driver , DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Driver currentDriver = getCurrentDriver();
        return rideService.getAllRidesOfDriver(currentDriver , pageRequest)
                .map(ride -> modelMapper.map(ride , RideDto.class)
                );
    }

    @Override
    public Driver getCurrentDriver() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return driverRepository.findByUser(user).orElseThrow(() ->
                new ResourceNotFoundException("Driver is not Associated with user with id "+user.getId())
        );
    }

    @Override
    public Driver updateDriveAvailabilty(Driver driver, boolean available) {
        driver.setAvailable(available);
        return driverRepository.save(driver);
    }

    @Override
    public Driver createNewDriver(Driver driver) {
        return driverRepository.save(driver);
    }


}
