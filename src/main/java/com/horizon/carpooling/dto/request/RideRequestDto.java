package com.horizon.carpooling.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDto {
    private int requestedSeats;
    private Date createdAt;
}
