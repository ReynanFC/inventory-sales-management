package com.reynan.inventorysalesmanagement.exceptions.handler;

import com.reynan.inventorysalesmanagement.exceptions.StandardError;
import com.reynan.inventorysalesmanagement.exceptions.StockException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(StockException.class)
    public ResponseEntity<StandardError> handleStockException(StockException exception, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Stock error";

        return ResponseEntity.status(status)
                .body(
                        buildError(status, error, exception, request)
                );
    }
}
