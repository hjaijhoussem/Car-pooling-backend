package com.horizon.carpooling.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum ErrorEnum {
    USER_NOT_FOUND("User not found"),
    EMAIL_EXIST("Email already exist"),
    INCORRECT_PASSWORD("Incorrect password"),
    RIDE_NOT_FOUND("Ride not found"),
    NO_AVAILABLE_SEATS("Sorry, There is no available seats"),
    RIDE_ALREADY_REQUESTED("Looks like you've already requested this ride");
    private final String message;
}
