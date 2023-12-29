package com.horizon.carpooling.dto.ride;

import com.horizon.carpooling.dto.request.RideRequestDetailDto;
import com.horizon.carpooling.dto.review.ReviewListDto;
import com.horizon.carpooling.dto.user.UserListDto;
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
public class RideDetailDto {
    private Long id;
    private int availableSeats;
    private float pricePerSeat;
    private Date createdAt;
    private Date updatedAt;
    private RideStatus status;
    private Date departureDate;
    private UserListDto driver ;
    private Region departureRegion;
    private Region destinationRegion;
    private String departureCity;
    private String destinationCity;
    private List<RideRequestDetailDto> rideRequests ;
    private List<ReviewListDto> reviews;
}
