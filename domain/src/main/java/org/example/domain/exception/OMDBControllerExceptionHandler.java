package org.example.domain.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class OMDBControllerExceptionHandler {

    @ExceptionHandler(FilmNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<OMDBControllerExceptionResponse> handleContactNotFoundException(FilmNotFoundException exception, HttpServletRequest request) {

        var response = new OMDBControllerExceptionResponse(HttpStatus.NOT_FOUND, request.getRequestURI(), exception.getClass().getName(), exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WebClientResponseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<OMDBControllerExceptionResponse> handleWebClientResponseException(WebClientResponseException exception, HttpServletRequest request) {

        var response = new OMDBControllerExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI(), exception.getClass().getName(), exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<OMDBControllerExceptionResponse> handleGenericException(Exception exception, HttpServletRequest request) {

        var response = new OMDBControllerExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI(), exception.getClass().getName(), exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<OMDBControllerExceptionResponse> handleIllegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {

        var response = new OMDBControllerExceptionResponse(HttpStatus.BAD_REQUEST, request.getRequestURI(), exception.getClass().getName(), exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<OMDBControllerExceptionResponse> handleNullPointerException(NullPointerException exception, HttpServletRequest request) {

        var response = new OMDBControllerExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI(), exception.getClass().getName(), exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
