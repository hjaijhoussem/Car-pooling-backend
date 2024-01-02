package com.horizon.carpooling.exception;

public class PhoneNumberAlreadyInUseException extends RuntimeException{
    public PhoneNumberAlreadyInUseException(){super(ErrorEnum.PHONE_NUMBER_ALREADY_IN_USE.getMessage());}
}
