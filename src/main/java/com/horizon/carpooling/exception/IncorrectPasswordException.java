package com.horizon.carpooling.exception;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException() {super(ErrorEnum.INCORRECT_PASSWORD.getMessage());}
}
