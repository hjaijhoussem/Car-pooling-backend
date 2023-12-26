package com.horizon.carpooling.entities;


import com.horizon.carpooling.entities.enums.Region;
import com.horizon.carpooling.entities.enums.RideStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank()
    @Min(1)
    @Max(1)
    private int availableSeats;
    @Column(nullable = false)
    @NotBlank
    private float pricePerSeat;
    @Column(nullable = false)
    private Date createdAt;

    private Date updatedAt ;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RideStatus status;
    @Column(nullable = false)
    @NotBlank()
    @Future()
    private Date departureDate;
    @Column(nullable = false)
    @NotBlank()
    private LocalTime departureTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "driver_id")
    private User driver ;




    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotBlank()
    private Region departureRegion;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Region destinationRegion;


    @Column(nullable = false)
    @NotBlank()
    private String departureCity;
    @Column(nullable = false)
    @NotBlank()
    private String destinationCity;
    @OneToMany(mappedBy = "ride")
    private List<RideRequest> rideRequests ;

    @OneToMany(mappedBy = "ride")
    private List<Review> reviews;

}
