package com.horizon.carpooling.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.horizon.carpooling.entities.enums.Region;
import com.horizon.carpooling.entities.enums.RideStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int availableSeats;
    @Column(nullable = false)

    private float pricePerSeat;
    @Column(nullable = false)
    private Date createdAt;

    private Date updatedAt ;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RideStatus status;
    @Column(nullable = false)
    private Date departureDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "driver_id")
    private User driver ;




    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Region departureRegion;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Region destinationRegion;


    @Column(nullable = false)
    private String departureCity;
    @Column(nullable = false)
    private String destinationCity;
    @OneToMany(mappedBy = "ride")
    private List<RideRequest> rideRequests ;

    @OneToMany(mappedBy = "ride")
    private List<Review> reviews;

}
