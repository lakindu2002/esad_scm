package com.esad.supply_chain_management.exceptions;

/**
 * Custom exception class that is thrown when resource cannot be created.
 * It calls the super constructor of the Exception class and passes the custom message.
 */
public class ResourceNotCreatedException extends Exception {
    public ResourceNotCreatedException(String message) {
        super(message);
    }
}
