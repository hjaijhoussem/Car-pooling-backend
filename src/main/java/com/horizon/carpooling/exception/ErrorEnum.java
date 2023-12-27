package com.horizon.carpooling.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InvalidClassException;

@AllArgsConstructor
@Getter
public enum ErrorEnum {
    USER_NOT_FOUND("User not found"),
    EMAIL_EXIST("Email already exist"),
    INCORRECT_PASSWORD("Incorrect password");
    private final String message;
}
