package com.horizon.carpooling.dto.user;

import com.horizon.carpooling.dto.request.RideRequestDetailDto;
import com.horizon.carpooling.dto.review.ReviewDetailDto;
import com.horizon.carpooling.dto.ride.RideDetailDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto {
    private Integer id;
    private String firstname;
    private String lastname;
    private String role;
    private long CIN;
    private long phoneNumber;
    private String email;
    private List<RideDetailDto> rides ;
    private List<RideRequestDetailDto> myRideRequests;
    private List<ReviewDetailDto> myReviews;
    private boolean isActive;
    private boolean isRider;
}
