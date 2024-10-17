package com.agyat.project.uber.uberApp.services.impl;

import com.agyat.project.uber.uberApp.services.DistanceService;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

@Service
public class DistanceServiceOSRMImpl implements DistanceService {
    @Override
    public double calculateDistance(Point src, Point dest) {
//TODO       CALL THE THIRD PARTY API CALLED OSRM TO CALCULATE DISTANCE
        return 0;
    }
}
