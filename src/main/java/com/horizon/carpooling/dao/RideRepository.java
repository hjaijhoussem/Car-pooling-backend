package com.horizon.carpooling.dao;

import com.horizon.carpooling.entities.Ride;
import com.horizon.carpooling.entities.User;
import com.horizon.carpooling.entities.enums.Region;
import com.horizon.carpooling.entities.enums.RideStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Driver;
import java.util.List;

@Repository
public interface RideRepository  extends JpaRepository<Ride, Long> {
    public List<Ride> findByDriver(User driver);
    // get ride by filter and pagination
    @Query(
            "SELECT r FROM Ride r WHERE " +
                    "(:departureCity IS NULL OR r.departureCity LIKE %:departureCity%) AND " +
                    "(:destinationCity IS NULL OR r.destinationCity LIKE %:destinationCity%) AND " +
                    "(:departureDate IS NULL OR r.departureDate >= :departureDate) AND " +
                    "(:availableSeats IS NULL OR r.availableSeats >= :availableSeats) AND " +
                    "(:pricePerSeat IS NULL OR r.pricePerSeat <= :pricePerSeat) AND " +
                    "(:departureRegion IS NULL OR r.departureRegion = :departureRegion) AND " +
                    "(:destinationRegion IS NULL OR r.destinationRegion = :destinationRegion) AND " +
                    "(:status IS NULL OR r.status = :status)" +
                    "ORDER BY r.createdAt DESC"
    )
    public List<Ride> findByFilter(String departureCity, String destinationCity, String departureDate,
                                   Integer availableSeats, Float pricePerSeat, Region departureRegion, Region destinationRegion,
                                   RideStatus status,
                                   Pageable pageable);
}
