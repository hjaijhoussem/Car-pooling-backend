package com.horizon.carpooling.controllers;

import com.horizon.carpooling.dto.review.ReviewCreateDto;
import com.horizon.carpooling.dto.review.ReviewDetailDto;
import com.horizon.carpooling.dto.review.ReviewUpdateDto;
import com.horizon.carpooling.dto.ride.RideCreateDto;
import com.horizon.carpooling.dto.ride.RideDetailDto;
import com.horizon.carpooling.services.ReviewService;
import com.horizon.carpooling.services.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/ride/{ride_id}/reviews")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class ReviewController {
        private final ReviewService reviewService ;

    @PostMapping()
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ReviewDetailDto> create(@RequestBody @Valid ReviewCreateDto reviewCreateDto,
                                                  @PathVariable Long ride_id
                                                  ) {
        return new ResponseEntity<>(this.reviewService.create(reviewCreateDto, ride_id), HttpStatus.CREATED);
    }


    @PostMapping("/{review_id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ReviewDetailDto> update(@RequestBody @Valid ReviewUpdateDto reviewUpdateDto,
                                                  @PathVariable Long review_id
                                                  ,  @PathVariable Long ride_id
    ) {
        return new ResponseEntity<>(this.reviewService.update(reviewUpdateDto,ride_id,review_id ), HttpStatus.CREATED);
    }



}
