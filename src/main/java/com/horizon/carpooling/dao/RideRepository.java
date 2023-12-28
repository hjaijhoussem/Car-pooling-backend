package com.horizon.carpooling.dao;

import com.horizon.carpooling.entities.Ride;
import com.horizon.carpooling.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Driver;
import java.util.List;

@Repository
public interface RideRepository  extends JpaRepository<Ride, Long> {
    public List<Ride> findByDriver(User driver);
}
