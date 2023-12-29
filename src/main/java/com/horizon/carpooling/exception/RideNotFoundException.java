package com.horizon.carpooling.exception;

public class RideNotFoundException extends RuntimeException {
    public RideNotFoundException(){super(ErrorEnum.RIDE_NOT_FOUND.getMessage());}
}
