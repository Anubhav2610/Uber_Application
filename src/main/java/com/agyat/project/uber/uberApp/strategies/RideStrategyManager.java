package com.agyat.project.uber.uberApp.strategies;

import com.agyat.project.uber.uberApp.strategies.impl.DriverMatchingHighestRatedDriverMatching;
import com.agyat.project.uber.uberApp.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.agyat.project.uber.uberApp.strategies.impl.RideFareDefaultFareCalculationStrategy;
import com.agyat.project.uber.uberApp.strategies.impl.RideFareSurgePricingFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;


@Component
@RequiredArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingNearestDriverStrategy nearestDriverStrategy;
    private final DriverMatchingHighestRatedDriverMatching highestRatedDriverMatching;
    private final RideFareDefaultFareCalculationStrategy defaultFareCalculationStrategy;
    private final RideFareSurgePricingFareCalculationStrategy surgePricingFareCalculationStrategy;

    public DriverMatchingStrategy driverMatchingStrategy(double riderRating){
        if(riderRating >= 4.6){
            return highestRatedDriverMatching;
        }else {
           return nearestDriverStrategy;
        }
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy(){
        //6PM TO 9PM

        LocalTime surgeStartTime = LocalTime.of(18 , 0);
        LocalTime surgeEndTime = LocalTime.of(21 , 0);
        LocalTime currentTime = LocalTime.now();

        boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);
        if(isSurgeTime){
            return surgePricingFareCalculationStrategy;
        }else{
            return defaultFareCalculationStrategy;
        }
    }
}
