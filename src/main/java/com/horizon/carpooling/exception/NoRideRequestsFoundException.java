package com.horizon.carpooling.exception;

public class NoRideRequestsFoundException extends RuntimeException {
    public NoRideRequestsFoundException() {super(ErrorEnum.NO_RIDE_REQUESTS_FOUND.getMessage());}

}
