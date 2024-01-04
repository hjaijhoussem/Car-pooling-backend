package com.horizon.carpooling.services;

import com.horizon.carpooling.dao.RideRepository;
import com.horizon.carpooling.dao.RideRequestRepository;
import com.horizon.carpooling.dao.UserRepository;
import com.horizon.carpooling.dto.request.RideRequestListDto;
import com.horizon.carpooling.dto.request.RideRequestDetailDto;
import com.horizon.carpooling.dto.request.RideRequestDto;
import com.horizon.carpooling.entities.Ride;
import com.horizon.carpooling.entities.RideRequest;
import com.horizon.carpooling.entities.User;
import com.horizon.carpooling.entities.enums.RideRequestStatus;
import com.horizon.carpooling.exception.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RideRequestService extends AbstractService {
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

    // accept ride request
    public RideRequestListDto acceptRideRequest(Long ride_id, Long rideRequestId) {

        User user = this.getUser();
        if (!user.isDriver())
        {

            throw new RuntimeException("You are not a driver");
        }
        Ride ride = rideDao.findById(ride_id).orElseThrow(RideNotFoundException::new);
        if(ride.getDriver().getId()!=user.getId())
        {
            throw new RuntimeException("You are not the driver of this ride");
        }

        RideRequest rideRequest = rideRequestDao.findById(rideRequestId).orElseThrow(RideRequestNotFoundException::new);
        if(rideRequest.getStatus() != RideRequestStatus.PENDING) throw new RuntimeException("Ride request already accepted");
        if(rideRequest.getRide().getId() != ride.getId()) throw new RideRequestNotFoundException();
        if(rideRequest.getRide().getAvailableSeats() - rideRequest.getRequestedSeats() < 0) throw new NoAvailableSeatsException();
        rideRequest.setStatus(RideRequestStatus.ACCEPTED);
        rideRequestDao.save(rideRequest);
        ride.setAvailableSeats(ride.getAvailableSeats() - rideRequest.getRequestedSeats());
        rideDao.save(ride);
        return mapper.map(rideRequest, RideRequestListDto.class);
    }

    public List<RideRequestListDto> getDriverRideRequests(Long ride_id) {
        User user = this.getUser();
        if (!user.isDriver())
        {
            throw new RuntimeException("You are not a driver");
        }
        Ride ride = rideDao.findById(ride_id).orElseThrow(RideNotFoundException::new);
        if(ride.getDriver().getId()!=user.getId())
        {
            throw new RuntimeException("You are not the driver of this ride");
        }
        List<RideRequest> rideRequests = rideRequestDao.findByRide(ride);
        return rideRequests.stream().map(rideRequest -> mapper.map(rideRequest, RideRequestListDto.class)).toList();
    }

    // reject ride request
    public RideRequestListDto rejectRideRequest(Long ride_id, Long rideRequestId) {
        User user = this.getUser();
        if (!user.isDriver())
        {
            throw new RuntimeException("You are not a driver");
        }
        Ride ride = rideDao.findById(ride_id).orElseThrow(RideNotFoundException::new);
        if(ride.getDriver().getId()!=user.getId())
        {
            throw new RuntimeException("You are not the driver of this ride");
        }
        RideRequest rideRequest = rideRequestDao.findById(rideRequestId).orElseThrow(RideRequestNotFoundException::new);
        if(rideRequest.getStatus() != RideRequestStatus.PENDING) throw new RuntimeException("Ride request already accepted or rejected");
        if(rideRequest.getRide().getId() != ride.getId()) throw new RideRequestNotFoundException();
        rideRequest.setStatus(RideRequestStatus.REJECTED);
        rideRequestDao.save(rideRequest);
        return mapper.map(rideRequest, RideRequestListDto.class);
    }

}
