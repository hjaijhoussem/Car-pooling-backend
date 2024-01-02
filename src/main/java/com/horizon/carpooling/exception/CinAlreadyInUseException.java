package com.horizon.carpooling.exception;

public class CinAlreadyInUseException extends RuntimeException {
    public CinAlreadyInUseException(){super(ErrorEnum.CIN_ALREADY_IN_USE.getMessage());}
}
