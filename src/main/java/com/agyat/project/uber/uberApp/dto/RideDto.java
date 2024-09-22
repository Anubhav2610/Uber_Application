package com.agyat.project.uber.uberApp.dto;

import com.agyat.project.uber.uberApp.entities.Driver;
import com.agyat.project.uber.uberApp.entities.Rider;
import com.agyat.project.uber.uberApp.entities.enums.PaymentMethod;
import com.agyat.project.uber.uberApp.entities.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDto {
    private Long id;


    private Point pickUpLocation;

    private Point dropOffLocation;

    private LocalDateTime createTime;

    private RiderDto rider;

    private DriverDto driver;

    private RideStatus ridestatus;

    private PaymentMethod paymentMethod;

    private Double fare;
    private LocalDateTime startedAt;
    private  LocalDateTime endedAt;
}
