package com.horizon.carpooling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long id;
    private float Stars;
    private String comment;
    private UserDto reviewer;
    private RideDto ride;
}
