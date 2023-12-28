package com.horizon.carpooling.services;

import com.horizon.carpooling.dao.RideRepository;
import com.horizon.carpooling.dao.UserRepository;
import com.horizon.carpooling.dto.ride.RideCreateDto;
import com.horizon.carpooling.dto.ride.RideDetailDto;
import com.horizon.carpooling.entities.Ride;
import com.horizon.carpooling.entities.User;
import com.horizon.carpooling.entities.enums.RideStatus;
import com.horizon.carpooling.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RideService {
    private final RideRepository rideDao;
    private final UserRepository userDao;
    private final ModelMapper mapper;
    private final AuthenticationService authenticationService;

    public RideDetailDto create(RideCreateDto rideCreateDto){
      Ride ride =   this.mapper.map(rideCreateDto,Ride.class);
      User driver = this.getUser();
        if(!driver.isDriver())
            throw new RuntimeException("You are not a driver");
        ride.setDriver(driver);
        ride.setCreatedAt(new Date( System.currentTimeMillis()));
        ride.setStatus(RideStatus.PENDING);
        this.rideDao.saveAndFlush(ride);
        return this.mapper.map(ride,RideDetailDto.class);
    }

    public RideDetailDto getRideDetail(Long id){
        Optional<Ride> ride = this.rideDao.findById(id);
        if(ride.isPresent()){
            return this.mapper.map(ride.get(),RideDetailDto.class);
        }
        else {
            throw new RuntimeException("Ride not found");
        }
    }


    public User getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> user =   userDao.findByEmail(username);
       if(user.isPresent()){
           return user.get();
       }
       else {
           throw new UserNotFoundException();
       }

    }
}
