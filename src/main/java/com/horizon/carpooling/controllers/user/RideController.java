package com.horizon.carpooling.controllers.user;

import com.horizon.carpooling.dto.ride.RideCreateDto;
import com.horizon.carpooling.dto.ride.RideDetailDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/rides")
public class RideController {

    @PostMapping()
    public ResponseEntity<RideDetailDto> create(@RequestBody @Valid RideCreateDto rideDto){
            return new ResponseEntity<RideDetailDto>(new RideDetailDto(), HttpStatus.CREATED);
    }
}
