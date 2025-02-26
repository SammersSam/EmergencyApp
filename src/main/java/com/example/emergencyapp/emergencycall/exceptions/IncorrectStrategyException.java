package com.example.emergencyapp.emergencycall.exceptions;

public class IncorrectStrategyException extends RuntimeException{
    public IncorrectStrategyException(String message) {
        super(message);
    }

    public IncorrectStrategyException(String message, Throwable cause) {
        super(message, cause);
    }
}
