package com.horizon.carpooling.dao;

import com.horizon.carpooling.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
