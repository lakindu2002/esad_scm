package com.esad.supply_chain_management.exceptions;

/**
 * Custom exception class that is thrown when resource cannot be found.
 * It calls the super constructor of the Exception class and passes the custom message.
 */
public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
