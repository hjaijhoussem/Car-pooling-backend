package com.horizon.carpooling.dto.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDto {
    @NotBlank(message = "Firstname is required")
    private String firstname;
    @NotBlank(message = "Lastname is required")
    private String lastname;
    @NotNull(message = "Phone number is required")
    @Min(value = 10000000L, message = "Phone number must be at least 8 digits")
    @Max(value = 99999999L, message = "Phone number cannot exceed 8 digits")
    private long phoneNumber;
    @Email
    private String email;
}
