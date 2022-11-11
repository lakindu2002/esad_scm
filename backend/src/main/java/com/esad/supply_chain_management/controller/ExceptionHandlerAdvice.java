package com.esad.supply_chain_management.controller;

import com.esad.supply_chain_management.dto.CustomDTO;
import com.esad.supply_chain_management.exceptions.ResourceCannotBeDeletedException;
import com.esad.supply_chain_management.exceptions.ResourceExistsException;
import com.esad.supply_chain_management.exceptions.ResourceNotCreatedException;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * A response filter defined to manage exceptions.
 * Each exception type is defined by `@ExceptionHandler` and in case of any error thrown that is defined here,
 * Its relevant callback is executed.
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {
    /**
     * Handle any not found exception
     *
     * @param ex The exception object that was thrown in the business layer
     * @return A 404 response
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                new CustomDTO(404, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * Handle any duplicate entry exceptions
     *
     * @param ex Exception thrown in business layer
     * @return The HTTP 409 response
     */
    @ExceptionHandler(ResourceExistsException.class)
    public ResponseEntity<CustomDTO> handleResourceExistsException(ResourceExistsException ex) {
        return new ResponseEntity<>(
                new CustomDTO(409, ex.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    /**
     * Handle an exception when resource cannot be deleted
     *
     * @param ex The exception thrown in business layer (Service)
     * @return The 400 bad request HTTP response
     */
    @ExceptionHandler(ResourceCannotBeDeletedException.class)
    public ResponseEntity<CustomDTO> handleResourceCannotBeDeletedException(ResourceCannotBeDeletedException ex) {
        return new ResponseEntity<>(
                new CustomDTO(400, ex.getMessage()),
                HttpStatus.BAD_GATEWAY
        );
    }

    /**
     * When all exceptions defined above do not get thrown, create generic exception handler for all classes
     *
     * @param ex The generic exception thrown
     * @return The 500 HTTP Response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomDTO> handleGenericException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                new CustomDTO(500, "We ran into an unexpected error."),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    /**
     * Error handler for resource not created
     *
     * @param ex The error object
     * @return The 406 http error code.
     */
    @ExceptionHandler(ResourceNotCreatedException.class)
    public ResponseEntity<CustomDTO> handleResourceNotCreatedException(ResourceNotCreatedException ex) {
        return new ResponseEntity<>(
                new CustomDTO(406, ex.getMessage()),
                HttpStatus.NOT_ACCEPTABLE
        );
    }
}
