package com.esad.supply_chain_management.exceptions;

/**
 * Custom exception class that is thrown when resource already exists.
 * It calls the super constructor of the Exception class and passes the custom message.
 */
public class ResourceExistsException extends Exception {
    public ResourceExistsException(String message) {
        super(message);
    }
}
