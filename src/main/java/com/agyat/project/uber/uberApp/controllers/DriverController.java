package com.agyat.project.uber.uberApp.controllers;

import com.agyat.project.uber.uberApp.dto.*;
import com.agyat.project.uber.uberApp.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping(path = "/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDto> acceptRide(@PathVariable long rideRequestId){
        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
    }

    @PostMapping(path = "/startRide/{rideRequestId}")
    public ResponseEntity<RideDto> startRide(@PathVariable long rideRequestId
                                            , @RequestBody StartRideDto startRideDto) {
        return ResponseEntity.ok(driverService.startRide(rideRequestId , startRideDto.getOtp()));
    }

    @PostMapping(path = "/endRide/{rideId}")
    public ResponseEntity<RideDto> endRide(@PathVariable long rideId){
        return ResponseEntity.ok(driverService.endRide(rideId));
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(driverService.cancelRide(rideId));
    }

    @PostMapping("/rateRider")
    public ResponseEntity<RiderDto> rateRider(@RequestBody RatingDto ratingDto){
        return ResponseEntity.ok(driverService.rateRider(ratingDto.getRideId() , ratingDto.getRating()));
    }

    @GetMapping("/getMyProfile")
    public  ResponseEntity<DriverDto> getMyProfile(){
        return ResponseEntity.ok(driverService.getMyProfile());
    }

    @GetMapping("/getMyRides")
    public  ResponseEntity<Page<RideDto>> getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffSet,
                                                        @RequestParam(defaultValue = "10" , required = false) Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffSet , pageSize ,
                Sort.by(Sort.Direction.DESC , "createdTime" , "id"));
        return ResponseEntity.ok(driverService.getAllMyRides(pageRequest));
    }

    @PostMapping("/rateRider/{rideId}/{rating}")
    public ResponseEntity<RiderDto> rateRider(@PathVariable Long rideId , @PathVariable Integer rating){
        return ResponseEntity.ok(driverService.rateRider(rideId , rating));
    }
}
