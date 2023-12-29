package com.horizon.carpooling.exception;

public class NoAvailableSeatsException extends RuntimeException {
    public NoAvailableSeatsException(){super(ErrorEnum.NO_AVAILABLE_SEATS.getMessage());}
}
