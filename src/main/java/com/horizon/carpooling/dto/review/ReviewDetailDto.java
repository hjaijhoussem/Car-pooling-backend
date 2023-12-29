package com.horizon.carpooling.dto.review;

import com.horizon.carpooling.dto.user.UserDetailDto;
import com.horizon.carpooling.dto.ride.RideDetailDto;
import com.horizon.carpooling.dto.user.UserListDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDetailDto {
    private Long id;
    private float Stars;
    private String comment;
    private RideDetailDto ride;
}
