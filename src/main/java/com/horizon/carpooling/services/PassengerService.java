package com.horizon.carpooling.services;

import com.horizon.carpooling.dao.RideRepository;
import com.horizon.carpooling.dao.RideRequestRepository;
import com.horizon.carpooling.dao.UserRepository;
import com.horizon.carpooling.dto.request.RequestListDto;
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
        //check if the Ride is already requested by this user , else save it
        RideRequest rideRequest = mapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setPassenger(authenticatedUser);
        rideRequest.setRide(ride);
        rideRequest.setStatus(RideRequestStatus.PENDING);
        boolean exist = ride.getRideRequests().stream().filter(rq -> rq.equals(rideRequest)).findFirst().isPresent();
        if (exist) throw new RideAlreadyRequestedException();
        rideRequestDao.save(rideRequest);
        // add rideRequest to the Ride rideRequests list and save
        ride.getRideRequests().add(rideRequest);
        rideDao.save(ride);
        // add rideRequest to the User rideRequest list and save
        authenticatedUser.getMyRideRequests().add(rideRequest);
        userDao.save(authenticatedUser);
    }

    public List<RequestListDto> getRideRequestList(UserDetails userDetails){
        User authenticatedUser =  userDao.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        if (authenticatedUser.getMyRideRequests().isEmpty()) throw new NoRideRequestsFoundException();
        return authenticatedUser.getMyRideRequests()
                .stream()
                .map(rideRequest -> mapper.map(rideRequest, RequestListDto.class))
                .collect(Collectors.toList());
    }
}
