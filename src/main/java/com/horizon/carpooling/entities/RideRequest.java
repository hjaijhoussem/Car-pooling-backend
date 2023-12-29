package com.horizon.carpooling.entities;

import com.horizon.carpooling.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class RideRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "passenger_id")
    private User passenger;
    @Column(nullable = false)
    @NotNull()
    private int requestedSeats;

    @Column(nullable = false)
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    @Column()
    private RideRequestStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "ride_id")
    private Ride ride  ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RideRequest that = (RideRequest) o;
        return passenger.getEmail() == that.passenger.getEmail();
    }

    @Override
    public int hashCode() {
        return Objects.hash(passenger.getEmail());
    }
}
