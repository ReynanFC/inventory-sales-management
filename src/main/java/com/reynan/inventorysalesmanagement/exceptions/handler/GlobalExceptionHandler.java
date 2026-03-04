package com.reynan.inventorysalesmanagement.exceptions.handler;

import com.reynan.inventorysalesmanagement.exceptions.BusinessException;
import com.reynan.inventorysalesmanagement.exceptions.ResourceNotFoundException;
import com.reynan.inventorysalesmanagement.exceptions.StandardError;
import com.reynan.inventorysalesmanagement.exceptions.StockException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.module.ResolutionException;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private StandardError buildError(
            HttpStatus status,
            String error,
            Exception ex,
            HttpServletRequest request
    ) {
        return new StandardError(
                Instant.now(),
                status.value(),
                error,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleAllExceptions(Exception exception, HttpServletRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String error = "Unexpected error";

        return ResponseEntity.status(status)
                .body(
                        buildError(status, error, exception, request)
                );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleResourceNotFoundException(
            ResourceNotFoundException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String error = "Resource not found";

        return ResponseEntity.status(status)
                .body(buildError(status, error, exception, request));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleValidationException(
            MethodArgumentNotValidException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Validation failed";

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        return ResponseEntity.status(status)
                .body(new StandardError(
                        Instant.now(),
                        status.value(),
                        error,
                        message,
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> handleBusinessException(
            BusinessException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Business rule violation";

        return ResponseEntity.status(status)
                .body(buildError(status, error, exception, request));
    }

    @ExceptionHandler(StockException.class)
    public ResponseEntity<StandardError> handleStockException(
            StockException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Stock error";

        return ResponseEntity.status(status)
                .body(buildError(status, error, exception, request));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> handleIllegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Invalid request";

        return ResponseEntity.status(status)
                .body(buildError(status, error, exception, request));
    }
}
