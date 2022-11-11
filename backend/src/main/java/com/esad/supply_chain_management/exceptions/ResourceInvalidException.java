package com.esad.supply_chain_management.exceptions;

/**
 * Custom exception class that is thrown when resource is invalid
 * It calls the super constructor of the Exception class and passes the custom message.
 */
public class ResourceInvalidException extends Exception {
    public ResourceInvalidException(String message) {
        super(message);
    }
}
