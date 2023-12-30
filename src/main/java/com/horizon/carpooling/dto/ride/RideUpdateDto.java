package com.horizon.carpooling.dto.ride;

import com.horizon.carpooling.entities.enums.Region;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideUpdateDto {

    private int availableSeats;
    private float pricePerSeat;
    @Future
    private Date departureDate;
    private Region departureRegion;
    private Region destinationRegion;
    private String departureCity;
    private String destinationCity;
    public void setDestinationRegion(String destinationRegion) {
        destinationRegion = destinationRegion.replace(" ", "_").toUpperCase();
        this.destinationRegion = Region.valueOf(destinationRegion.toUpperCase());
    }
    public void setDepartureRegion(String departureRegion) {
        departureRegion = departureRegion.replace(" ", "_").toUpperCase();
        this.departureRegion = Region.valueOf(departureRegion.toUpperCase());
    }
}
