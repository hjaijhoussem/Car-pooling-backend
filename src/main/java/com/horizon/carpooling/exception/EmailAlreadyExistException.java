package com.horizon.carpooling.exception;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException(){super(ErrorEnum.EMAIL_EXIST.getMessage());}
}
