package com.nagarro.exceptions;

import java.time.LocalDateTime;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nagarro.dto.ErrorResponse;

/**
 * Global Exception Handler responsible for handling various exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles general exceptions and returns an internal server error response.
     *
     * @param ex The exception to handle.
     * @return ResponseEntity containing the error response.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = createErrorResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles custom exceptions and returns a bad request response.
     *
     * @param ex The custom exception to handle.
     * @return ResponseEntity containing the error response.
     */
    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorResponse errorResponse = createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles illegal argument exceptions and returns a bad request response.
     *
     * @param ex The illegal argument exception to handle.
     * @return ResponseEntity containing the error response.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles not found exceptions and returns a not found response.
     *
     * @param ex The not found exception to handle.
     * @return ResponseEntity containing the error response.
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse errorResponse = createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Creates an error response with the given message and status.
     *
     * @param message The error message.
     * @param status  The HTTP status code.
     * @return The created ErrorResponse.
     */
    private ErrorResponse createErrorResponse(String message, HttpStatus status) {
        return new ErrorResponse(message, status.value(), LocalDateTime.now());
    }
}