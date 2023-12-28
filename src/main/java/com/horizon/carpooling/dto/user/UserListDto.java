package com.horizon.carpooling.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDto {

        private Integer id;
        private String firstname;
        private String lastname;
        private String role;
        private long CIN;
        private long phoneNumber;
        private String email;
        private boolean isActive;
        private boolean isRider;


}
