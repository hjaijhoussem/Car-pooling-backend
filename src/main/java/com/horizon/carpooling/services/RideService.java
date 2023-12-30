package com.horizon.carpooling.services;

import com.horizon.carpooling.dao.RideRepository;
import com.horizon.carpooling.dao.UserRepository;
import com.horizon.carpooling.dto.ride.RideCreateDto;
import com.horizon.carpooling.dto.ride.RideDetailDto;
import com.horizon.carpooling.dto.ride.RideListDto;
import com.horizon.carpooling.dto.ride.RideUpdateDto;
import com.horizon.carpooling.entities.Ride;
import com.horizon.carpooling.entities.User;
import com.horizon.carpooling.entities.enums.Region;
import com.horizon.carpooling.entities.enums.RideStatus;
import com.horizon.carpooling.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Internal;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


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

    public RideDetailDto update(RideUpdateDto rideUpdateDto,Long id){
        Ride ride = this.rideDao.findById(id).orElseThrow(RuntimeException::new);
        if(ride.getStatus() != RideStatus.PENDING)
            throw new RuntimeException("You can't update this ride");
        User driver = this.getUser();
        if(!Objects.equals(driver.getId(), ride.getDriver().getId()))
            throw new RuntimeException("You are not the driver of this ride");
        if(!driver.isDriver())
            throw new RuntimeException("You are not a driver");
        this.mapper.map(rideUpdateDto,ride);
        ride.setDriver(driver);
        ride.setUpdatedAt(new Date( System.currentTimeMillis()));
        ride.setStatus(RideStatus.PENDING);
        this.rideDao.saveAndFlush(ride);
        return this.mapper.map(ride,RideDetailDto.class);
    }

    public List<RideListDto> getDriverRides(int id){
        //get driver
        User driver = this.userDao.findById(id).orElseThrow(UserNotFoundException::new);
        if(!driver.isDriver())
            throw new RuntimeException("This user is not a driver");
        List<Ride> rides = this.rideDao.findByDriver(driver);
        return rides.stream().map(ride -> this.mapper.map(ride,RideListDto.class)).toList();
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

    public List<RideListDto> getRides(

             RideStatus status ,
                Integer driverId,
             String departureCity,
            String destinationCity,
            Date departureDate,
            Float pricePerSeat,
             Integer availableSeats,
            Region departureRegion,
            Region destinationRegion,
             Integer page ,
             Integer size

    ) {
        // find all rides
        // get by filter and pagination
        User driver = null;
        if (driverId != null) {
            driver = this.userDao.findById(driverId).orElseThrow(UserNotFoundException::new);
            if (!driver.isDriver())
                throw new RuntimeException("This user is not a driver");
        }
        if (page == null)
            page = 0;
        if (size == null)
            size = 10;
        Pageable pageable = PageRequest.of(page, size);
        // get by filter
        List<Ride> rides = this.rideDao.findByFilter(departureCity, destinationCity, departureDate, availableSeats, pricePerSeat,
                driver, departureRegion, destinationRegion, status, pageable);

        return rides.stream().map(ride -> this.mapper.map(ride, RideListDto.class)).toList();
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

    public List<String> getRegions() {
        return List.of(Region.values()).stream().map(name -> {
            String region = name.toString();
            region = region.replace("_", " ");
            return region.substring(0, 1).toUpperCase() + region.substring(1).toLowerCase();

        }).toList();
    }

}
