package com.example.emergencyapp.emergencycall.exceptions;

public class NotAvailableResourcesExceptions extends RuntimeException{
    public NotAvailableResourcesExceptions(String message) {
        super(message);
    }

    public NotAvailableResourcesExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
