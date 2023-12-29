package com.horizon.carpooling.dao;

import com.horizon.carpooling.dto.request.RequestListDto;
import com.horizon.carpooling.entities.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest,Long> {

    @Query("SELECT r FROM RideRequest r WHERE r.passenger.email = :userEmail AND r.ride.id = :rideId")
    Optional<RideRequest> findByUserAndRide(String userEmail, Long rideId);

    @Query("SELECT r FROM RideRequest r WHERE r.passenger.email = :userEmail")
    List<RideRequest> findByUserEmail(String userEmail);
}
