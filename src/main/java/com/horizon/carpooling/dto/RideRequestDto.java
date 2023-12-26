package com.horizon.carpooling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDto {
    private Long id;
    private UserDto passenger;
    private int requestedSeats;
    private Date createdAt;
    private boolean isAccepted;
    private RideDto ride  ;
}
