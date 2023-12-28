package com.horizon.carpooling.dto.ride;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.horizon.carpooling.entities.enums.Region;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideCreateDto {
  
    @Positive
    @Max(8)
    private int availableSeats;
    @Positive(message = "Price per seat must be positive")
    @NotNull(message = "Price per seat must not be null")
    private float pricePerSeat;
    @Future(message = "Departure date must be in the future")
    @NotNull(message = "Departure date must not be null")
    private Date departureDate;
    @NotNull(message = "Departure region must not be null")
    private Region departureRegion;
    @NotNull(message = "Destination region must not be null")
    private Region destinationRegion;
    @NotBlank(message = "Departure city must not be blank")
    private String departureCity;
    @NotBlank(message = "Destination city must not be blank")
    private String destinationCity;
}
