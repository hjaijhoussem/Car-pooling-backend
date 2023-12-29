package com.horizon.carpooling.dto.request;

import com.horizon.carpooling.dto.user.UserListDto;
import com.horizon.carpooling.entities.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestListDto {

        private Long id;
        private UserListDto passenger;
        private int requestedSeats;
        private Date createdAt;
        private RideRequestStatus status;
}
