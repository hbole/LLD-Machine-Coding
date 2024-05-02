package com.bookmyshow.exceptions;

public class WrongUserCredentialsException extends Exception {
    public WrongUserCredentialsException(String message) {
        super(message);
    }
}
