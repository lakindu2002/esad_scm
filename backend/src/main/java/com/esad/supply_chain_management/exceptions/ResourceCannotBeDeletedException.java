package com.esad.supply_chain_management.exceptions;

/**
 * Custom exception class that is thrown when resource cannot be deleted.
 * It calls the super constructor of the Exception class and passes the custom message.
 */
public class ResourceCannotBeDeletedException extends Exception {
    public ResourceCannotBeDeletedException(String message) {
        super(message);
    }
}
