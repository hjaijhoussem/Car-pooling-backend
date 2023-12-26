package com.horizon.carpooling.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){super(ErrorEnum.USER_NOT_FOUND.getMessage());}
}
