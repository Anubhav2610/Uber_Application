package com.agyat.project.uber.uberApp.services.impl;

import com.agyat.project.uber.uberApp.dto.DriverDto;
import com.agyat.project.uber.uberApp.dto.RideDto;
import com.agyat.project.uber.uberApp.dto.RideRequestDto;
import com.agyat.project.uber.uberApp.dto.RiderDto;
import com.agyat.project.uber.uberApp.entities.*;
import com.agyat.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.agyat.project.uber.uberApp.entities.enums.RideStatus;
import com.agyat.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.agyat.project.uber.uberApp.repositories.RideRequestRepository;
import com.agyat.project.uber.uberApp.repositories.RiderRepository;
import com.agyat.project.uber.uberApp.services.DriverService;
import com.agyat.project.uber.uberApp.services.RideService;
import com.agyat.project.uber.uberApp.services.RiderService;
import com.agyat.project.uber.uberApp.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {


    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;

    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto , RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        log.info("Pickup Point : "+ rideRequest.getPickUpLocation());
        log.info("FropOff Point : "+ rideRequest.getDropOffLocation());
        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        List<Driver> drivers = rideStrategyManager
                .driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);

        //TODO : Send notification to all drivers about this ride request

        return modelMapper.map(savedRideRequest , RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider is Not Allowed to Cancel this Ride");
        }
        if(!ride.getRidestatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride Cannot be cancelled , invalid status : "+ ride.getRidestatus());
        }
        Ride savedRide = rideService.updateRideStatus(ride ,RideStatus.CANCELLED);

        driverService.updateDriveAvailabilty(ride.getDriver() , true);
        return modelMapper.map(ride , RideDto.class);
        }

    @Override
    public DriverDto rateRider(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        Rider rider = getCurrentRider();
        return modelMapper.map(rider , RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider rider = getCurrentRider();
        return rideService.getAllRidesOfRider(rider.getId() , pageRequest)
                .map(ride -> modelMapper.map(ride , RideDto.class));
    }


    @Override
    public Rider createNewRider(User savedUser) {
        Rider rider = Rider
                .builder()
                .user(savedUser)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
        //TODO :  implement Spring Security
        return riderRepository.findById(1l).orElseThrow(() -> new ResourceNotFoundException(
                "Rider Not Found With Id : " + 1
        ));
    }
}
