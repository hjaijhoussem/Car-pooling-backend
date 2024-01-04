package com.horizon.carpooling.controllers;

import com.horizon.carpooling.dto.request.RequestListDto;
import com.horizon.carpooling.dto.request.RideRequestDetailDto;
import com.horizon.carpooling.dto.request.RideRequestDto;
import com.horizon.carpooling.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class RideRequestController {

    private final RideRequestService rideRequestService;

    @PostMapping("/api/v1/passenger/{ride_id}/reserve")
    public ResponseEntity<String> reserveRide(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable("ride_id") Long rideId,
                                              @RequestBody RideRequestDto rideRequestDto){
        rideRequestService.reserveRide(userDetails,rideId,rideRequestDto);
        return ResponseEntity.ok("Ride request Submitted");
    }

    @GetMapping("/api/v1/passenger/ride_requests")
    public ResponseEntity<List<RideRequestDetailDto>> getPassengerRideRequestList(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(rideRequestService.getRideRequestList(userDetails));
    }

    @DeleteMapping ("/api/v1/passenger/ride_requests/{ride_id}/{ride_request_id}/cancel")
    public ResponseEntity<String> cancelRideRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("ride_id") Long rideId,
            @PathVariable("ride_request_id") Long rideRequestId){
        rideRequestService.cancelRideRequest(userDetails,rideId,rideRequestId);
        return ResponseEntity.ok("Ride request successfully deleted");
    }

    @GetMapping("/api/v1/driver/{ride_id}/accept/{ride_request_id}")
    public ResponseEntity<RequestListDto> acceptRide(
                                              @PathVariable("ride_id") Long rideId,
                                                @PathVariable("ride_request_id") Long rideRequestId
                                             ){

        return ResponseEntity.ok(rideRequestService.acceptRideRequest(rideId,rideRequestId));
    }
    @GetMapping("/api/v1/driver/{ride_id}/ride_requests")
    public ResponseEntity<List<RequestListDto>> getDriverRideRequests(
            @PathVariable("ride_id") Long rideId

    ){

        return ResponseEntity.ok(rideRequestService.getDriverRideRequests(rideId));
    }


    @GetMapping("/api/v1/driver/{ride_id}/ride_requests/{ride_request_id}/reject")
    public ResponseEntity<RequestListDto> rejectRideRequest(
            @PathVariable("ride_id") Long rideId,
            @PathVariable("ride_request_id") Long rideRequestId
    ){

        return ResponseEntity.ok(rideRequestService.rejectRideRequest(rideId,rideRequestId));
    }

}
