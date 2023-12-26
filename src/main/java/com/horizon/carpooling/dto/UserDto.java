package com.horizon.carpooling.dto;

import com.horizon.carpooling.entities.RideRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String firstname;
    private String lastname;
    private long CIN;
    private long phoneNumber;
    private String email;
    private List<RideDto> rides ;
    private List<RideRequest> myRideRequests;
    private List<ReviewDto> myReviews;
    private boolean isActive;
    private boolean isRider;
}
