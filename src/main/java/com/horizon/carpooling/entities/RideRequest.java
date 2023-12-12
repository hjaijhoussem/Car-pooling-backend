package com.horizon.carpooling.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class RideRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(nullable = false)
    private User passenger;
    @Column(nullable = false)
    private int requestedSeats;

    @Column(nullable = false)
    private Date createdAt;

    @Column()
    private boolean isAccepted;

    @ManyToOne()
    @JoinColumn(nullable = false)
    private Ride ride  ;
}
