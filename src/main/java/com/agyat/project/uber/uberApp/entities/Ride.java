package com.agyat.project.uber.uberApp.entities;

import com.agyat.project.uber.uberApp.entities.enums.PaymentMethod;
import com.agyat.project.uber.uberApp.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "idx_ride_rider" , columnList = "rider_id"),
        @Index(name = "idx_ride_driver" , columnList = "driver_id")
})
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(columnDefinition = "Geometry(Point , 4326)")
    private Point pickUpLocation;

    @Column(columnDefinition = "Geometry(Point , 4326)")
    private Point dropOffLocation;

    @CreationTimestamp
    private LocalDateTime createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;

    @ManyToOne(fetch = FetchType.LAZY)
    private Driver driver;

    @Enumerated(EnumType.STRING)
    private RideStatus ridestatus;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private Double fare;
    private LocalDateTime startedAt;
    private  LocalDateTime endedAt;
    private String otp;
}
