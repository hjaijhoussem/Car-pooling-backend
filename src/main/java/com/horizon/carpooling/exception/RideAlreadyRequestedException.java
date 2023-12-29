package com.horizon.carpooling.exception;

public class RideAlreadyRequestedException extends RuntimeException {
    public RideAlreadyRequestedException() {super(ErrorEnum.RIDE_ALREADY_REQUESTED.getMessage());}

}
