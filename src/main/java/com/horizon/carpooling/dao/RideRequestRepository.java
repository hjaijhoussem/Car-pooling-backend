package com.horizon.carpooling.dao;

import com.horizon.carpooling.dto.request.RequestListDto;
import com.horizon.carpooling.entities.Ride;
import com.horizon.carpooling.entities.RideRequest;
import com.horizon.carpooling.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    List<RideRequest> findByRideAndPassenger(Ride ride, User passenger);

    @Transactional
    @Modifying
    @Query("DELETE FROM RideRequest r WHERE r.passenger.email = :userEmail")
    void deleteByUserEmail(String userEmail);

    public List<RideRequest> findByRide(Ride ride);

}
