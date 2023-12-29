package com.horizon.carpooling.services;

import com.horizon.carpooling.dao.RideRepository;
import com.horizon.carpooling.dao.RideRequestRepository;
import com.horizon.carpooling.dao.UserRepository;
import com.horizon.carpooling.dto.request.RideRequestDetailDto;
import com.horizon.carpooling.dto.request.RideRequestDto;
import com.horizon.carpooling.entities.Ride;
import com.horizon.carpooling.entities.RideRequest;
import com.horizon.carpooling.entities.User;
import com.horizon.carpooling.entities.enums.RideRequestStatus;
import com.horizon.carpooling.exception.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PassengerService {
    private final RideRequestRepository rideRequestDao;
    private final UserRepository userDao;
    private final RideRepository rideDao;
    private final ModelMapper mapper;


    public void reserveRide(UserDetails userDetails, Long rideId, RideRequestDto rideRequestDto){
        // retrieve authenticated user
        User authenticatedUser =  userDao.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        // check existing for ride
        Ride ride = rideDao.findById(rideId).orElseThrow(RideNotFoundException::new);
        // check available seats in ride
        if(ride.getAvailableSeats() - rideRequestDto.getRequestedSeats() < 0) throw new NoAvailableSeatsException();
        // check if the Ride is already requested by this user
        Optional<RideRequest> rideRequest = rideRequestDao.findByUserAndRide(authenticatedUser.getEmail(), rideId);
        if (rideRequest.isPresent()) throw new RideAlreadyRequestedException();
        // save rideRequest
        RideRequest request = RideRequest.builder()
                .passenger(authenticatedUser)
                .requestedSeats(rideRequestDto.getRequestedSeats())
                .createdAt(rideRequestDto.getCreatedAt())
                .status(RideRequestStatus.PENDING)
                .ride(ride)
                .build();
        rideRequestDao.save(request);
    }

    public List<RideRequestDetailDto> getRideRequestList(UserDetails userDetails){
        User authenticatedUser =  userDao.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        return rideRequestDao.findByUserEmail(authenticatedUser.getEmail())
                .stream()
                .map(rideRequest -> mapper.map(rideRequest, RideRequestDetailDto.class))
                .collect(Collectors.toList());
    }

    public void cancelRideRequest(UserDetails userDetails, Long ride_id, Long rideRequestId){
        User authenticatedUser =  userDao.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        Ride ride = rideDao.findById(ride_id).orElseThrow(RideNotFoundException::new);
        RideRequest rideRequest = rideRequestDao.findByUserAndRide(authenticatedUser.getEmail(),ride_id)
                .orElseThrow(RideRequestNotFoundException::new);
        rideRequestDao.deleteById(rideRequestId);
    }

}
