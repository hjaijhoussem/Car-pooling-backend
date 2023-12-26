package com.horizon.carpooling.dto;

import com.horizon.carpooling.entities.enums.Region;
import com.horizon.carpooling.entities.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDto {
    private Long id;
    private int availableSeats;
    private float pricePerSeat;
    private Date createdAt;
    private Date updatedAt ;
    private RideStatus status;
    private Date departureDate;
    private LocalTime departureTime;
    private UserDto driver ;
    private Region departureRegion;
    private Region destinationRegion;
    private String departureCity;
    private String destinationCity;
    private List<RideRequestDto> rideRequests ;
    private List<ReviewDto> reviews;
}
