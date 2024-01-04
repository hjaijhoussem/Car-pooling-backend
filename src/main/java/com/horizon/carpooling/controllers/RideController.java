package com.horizon.carpooling.controllers;

import com.horizon.carpooling.dto.ride.RideCreateDto;
import com.horizon.carpooling.dto.ride.RideDetailDto;
import com.horizon.carpooling.dto.ride.RideListDto;
import com.horizon.carpooling.dto.ride.RideUpdateDto;
import com.horizon.carpooling.entities.enums.Region;
import com.horizon.carpooling.entities.enums.RideStatus;
import com.horizon.carpooling.services.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.Internal;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class RideController {
    private final RideService rideService;

    @PostMapping("/rides")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<RideDetailDto> create(@RequestBody @Valid RideCreateDto rideDto) {
        return new ResponseEntity<>(this.rideService.create(rideDto), HttpStatus.CREATED);
    }

    @PutMapping("/rides/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<RideDetailDto> update(@RequestBody @Valid RideUpdateDto rideDto, @PathVariable Long id) {
        return new ResponseEntity<>(this.rideService.update(rideDto,id), HttpStatus.CREATED);
    }

    @GetMapping("/rides/{id}")
    public ResponseEntity<RideDetailDto> getRideDetail(@PathVariable Long id) {
        return new ResponseEntity<>(this.rideService.getRideDetail(id), HttpStatus.OK);
    }




    @GetMapping("/rides")
    public ResponseEntity<List<RideListDto>> getRides(
        @RequestParam(required = false,defaultValue = "PENDING") RideStatus status,
        @RequestParam(required = false) Integer driverId,
        @RequestParam(required = false) String departureCity,
        @RequestParam(required = false) String destinationCity,
        @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd") Date departureDate,
        @RequestParam(required = false) Float pricePerSeat,
        @RequestParam(required = false) Integer availableSeats
        ,@RequestParam(required = false) Region departureRegion
        ,@RequestParam(required = false) Region destinationRegion
        ,@RequestParam(required = false) Integer page
        ,@RequestParam(required = false) Integer size



    ) {

        return new ResponseEntity<>(this.rideService.getRides(
            status,
            driverId,
            departureCity,
            destinationCity,
            departureDate,
            pricePerSeat,
            availableSeats,
            departureRegion,
            destinationRegion,
            page,
            size
        ), HttpStatus.OK);
    }

    // get regions
    @GetMapping("/rides/meta-data/regions")
    public ResponseEntity<List<String>> getRegions() {
        return new ResponseEntity<>(this.rideService.getRegions(), HttpStatus.OK);
    }

}



