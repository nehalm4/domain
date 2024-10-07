package com.domain.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.domain.pojo.ErrorResponse;

/**
 * @author Nehal Mahajan
 * @apiNote Global exception handler class to handle all exception in application
 */
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())                
                .message(ex.getMessage())                      
                .details("A runtime exception occurred")       
                .statusCode(HttpStatus.BAD_REQUEST.value())    
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); 
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())                
                .message(ex.getMessage())                      
                .details("An unexpected error occurred")       
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()) 
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); 
    }
}

