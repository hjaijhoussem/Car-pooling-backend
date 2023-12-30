package com.horizon.carpooling.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewUpdateDto {
    @Min(value = 0,message = "min stars should be 0")
    @Max(value = 5,message = "max stars should not pass 5")
    private float Stars;
    private String comment;
}
