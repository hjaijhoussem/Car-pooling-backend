package com.horizon.carpooling.controllers.user;

import com.horizon.carpooling.dto.ride.RideCreateDto;
import com.horizon.carpooling.dto.ride.RideDetailDto;
import com.horizon.carpooling.dto.ride.RideUpdateDto;
import com.horizon.carpooling.services.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
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


}



