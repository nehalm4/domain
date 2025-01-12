package com.domain.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.domain.pojo.ErrorResponse;

/**
 * @author Nehal Mahajan
 * @apiNote Global exception handler class to handle all exception in
 *          application
 */
@ControllerAdvice
public class GlobalErrorHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalErrorHandler.class);

	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
		log.error("RuntimeException::: {}" + ex.getMessage());
		ErrorResponse errorResponse = ErrorResponse.builder().timestamp(LocalDateTime.now()).message(ex.getMessage())
				.details("A runtime exception occurred").statusCode(HttpStatus.BAD_REQUEST.value()).build();
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		log.error("Exception::: {}", ex.getMessage());
		ErrorResponse errorResponse = ErrorResponse.builder().timestamp(LocalDateTime.now()).message(ex.getMessage())
				.details("An unexpected error occurred").statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
