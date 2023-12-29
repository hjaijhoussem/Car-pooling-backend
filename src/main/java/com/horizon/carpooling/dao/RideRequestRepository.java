package com.horizon.carpooling.dao;

import com.horizon.carpooling.dto.request.RequestListDto;
import com.horizon.carpooling.entities.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest,Long> {
}
