package com.horizon.carpooling.services;

import com.horizon.carpooling.dao.ReviewRepository;
import com.horizon.carpooling.dao.RideRepository;
import com.horizon.carpooling.dao.RideRequestRepository;
import com.horizon.carpooling.dto.review.ReviewCreateDto;
import com.horizon.carpooling.dto.review.ReviewDetailDto;
import com.horizon.carpooling.dto.review.ReviewUpdateDto;
import com.horizon.carpooling.entities.Review;
import com.horizon.carpooling.entities.Ride;
import com.horizon.carpooling.entities.RideRequest;
import com.horizon.carpooling.entities.User;
import com.horizon.carpooling.entities.enums.RideStatus;
import com.horizon.carpooling.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class ReviewService extends AbstractService{
    private final ReviewRepository reviewRepository;
    private  final RideRepository rideRepository;
    private  final RideRequestRepository rideRequestRepository;





    public ReviewDetailDto create(ReviewCreateDto reviewCreateDto,Long ride_id){
        User user = this.getUser();
        Ride ride = this.rideRepository.findById(ride_id).orElseThrow(RuntimeException::new);
        List<RideRequest> requests = this.rideRequestRepository.findByRideAndPassenger(ride,user);
        if(requests.isEmpty()){
            throw  new RuntimeException("this user didn't participate on this ride");
        }
        if(requests.size()>1){
            throw  new RuntimeException("you can only put one review");
        }
        Review review =   this.mapper.map(reviewCreateDto,Review.class);
        review.setReviewer(user);
        review.setRide(ride);
        this.reviewRepository.save(review);
      return   this.mapper.map(review,ReviewDetailDto.class);
    }

    public ReviewDetailDto update(ReviewUpdateDto reviewUpdateDto, Long ride_id,Long review_id){
        User user = this.getUser();
        Review review = this.reviewRepository.findById(review_id).orElseThrow(RuntimeException::new);
        if(review.getReviewer().getId()!=user.getId()){
            throw new RuntimeException("only who create the the review can modify it");
        }
        this.mapper.map(reviewUpdateDto,review);
        this.reviewRepository.save(review);
        return   this.mapper.map(review,ReviewDetailDto.class);

    }

    public Review getReviewDetail(){
        return null;
    }


    public Review getReviews(){
        return null;
    }
}
