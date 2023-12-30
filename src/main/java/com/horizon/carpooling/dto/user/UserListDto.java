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
        private boolean isRider;


}
