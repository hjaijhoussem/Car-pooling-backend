package com.horizon.carpooling.exception;


public class RideRequestNotFoundException extends RuntimeException {
    public RideRequestNotFoundException(){super(ErrorEnum.RIDE_REQUEST_NOT_FOUND.getMessage());}

}
