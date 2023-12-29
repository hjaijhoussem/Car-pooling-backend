package com.horizon.carpooling.controllers;

import com.horizon.carpooling.dto.request.RequestListDto;
import com.horizon.carpooling.dto.request.RideRequestDto;
import com.horizon.carpooling.services.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passenger")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping("/{ride_id}/reserve")
    public ResponseEntity<String> reserveRide(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable("ride_id") Long rideId,
                                              @RequestBody RideRequestDto rideRequestDto){
        passengerService.reserveRide(userDetails,rideId,rideRequestDto);
        return ResponseEntity.ok("Ride request Submitted");
    }

    @GetMapping("/ride_requests")
    public ResponseEntity<List<RequestListDto>> getPassengerRideRequestList(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(passengerService.getRideRequestList(userDetails));
    }
}
