package com.horizon.carpooling.dao;

import com.horizon.carpooling.entities.Review;
import com.horizon.carpooling.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    public List<Review> findByRide(Ride ride);
}
