package com.horizon.carpooling.dto.ride;

import com.horizon.carpooling.entities.enums.Region;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideCreateDto {
  
    @NotBlank
    @Max(8L)
    private int availableSeats;
    @NotBlank
    private float pricePerSeat;
    @Future
    private Date departureDate;
    @NotBlank
    private LocalTime departureTime;
    @NotBlank
    private Region departureRegion;
    @NotBlank
    private Region destinationRegion;
    @NotBlank
    private String departureCity;
    @NotBlank
    private String destinationCity;
}
