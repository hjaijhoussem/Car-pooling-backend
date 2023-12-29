package com.horizon.carpooling.dto.user;

import com.horizon.carpooling.dto.request.RideRequestDetailDto;
import com.horizon.carpooling.dto.review.ReviewDetailDto;
import com.horizon.carpooling.dto.review.ReviewListDto;
import com.horizon.carpooling.dto.ride.RideDetailDto;
import com.horizon.carpooling.dto.ride.RideListDto;
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
    private List<RideListDto> rides ;
    private List<RideListDto> myRideRequests;
    private List<ReviewListDto> myReviews;
    private boolean isActive;
    private boolean isRider;
}
