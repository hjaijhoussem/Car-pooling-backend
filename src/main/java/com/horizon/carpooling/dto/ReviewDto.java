package com.horizon.carpooling.dto;

import com.horizon.carpooling.entities.Ride;
import com.horizon.carpooling.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private float Stars;
    private String comment;
    private UserDto reviewer;
    private RideDto ride;
}
