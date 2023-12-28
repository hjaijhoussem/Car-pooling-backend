package com.horizon.carpooling.dto.request;

import com.horizon.carpooling.dto.ride.RideDetailDto;
import com.horizon.carpooling.dto.user.UserDetailDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDetailDto {
    private Long id;
    private UserDetailDto passenger;
    private int requestedSeats;
    private Date createdAt;
    private boolean isAccepted;
    private RideDetailDto ride  ;
}
