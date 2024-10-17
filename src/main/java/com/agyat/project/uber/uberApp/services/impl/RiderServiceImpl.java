package com.agyat.project.uber.uberApp.services.impl;

import com.agyat.project.uber.uberApp.dto.DriverDto;
import com.agyat.project.uber.uberApp.dto.RideDto;
import com.agyat.project.uber.uberApp.dto.RideRequestDto;
import com.agyat.project.uber.uberApp.dto.RiderDto;
import com.agyat.project.uber.uberApp.entities.RideRequest;
import com.agyat.project.uber.uberApp.entities.Rider;
import com.agyat.project.uber.uberApp.entities.User;
import com.agyat.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.agyat.project.uber.uberApp.repositories.RideRequestRepository;
import com.agyat.project.uber.uberApp.repositories.RiderRepository;
import com.agyat.project.uber.uberApp.services.RiderService;
import com.agyat.project.uber.uberApp.strategies.DriverMatchingStrategy;
import com.agyat.project.uber.uberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {


    private final ModelMapper modelMapper;
    private final RideFareCalculationStrategy rideFareCalculationStrategy;
    private final DriverMatchingStrategy driverMatchingStrategy;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        RideRequest rideRequest = modelMapper.map(rideRequestDto , RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);

        double fare = rideFareCalculationStrategy.calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);
        driverMatchingStrategy.findMatchingDriver(rideRequest);

        return modelMapper.map(savedRideRequest , RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto rateRider(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
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
}
