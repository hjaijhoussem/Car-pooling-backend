package com.horizon.carpooling.auth;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Firstname is required")
    private String firstname;
    @NotBlank(message = "Lastname is required")
    private String lastname;
    @Email
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @NotNull(message = "CIN is required")
    @Max(value = 99999999L, message = "CIN cannot exceed 8 digits")
    @Positive(message = "CIN must be a positive number")
    private long CIN;

    @NotNull(message = "Phone number is required")
    @Min(value = 10000000L, message = "Phone number must be at least 8 digits")
    @Max(value = 99999999L, message = "Phone number cannot exceed 8 digits")
    private long phoneNumber;
}
