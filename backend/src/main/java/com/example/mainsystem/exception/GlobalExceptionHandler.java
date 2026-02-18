package com.example.mainsystem.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private ApiErrorResponse buildError(HttpStatus status,
                                        String message,
                                        Map<String, String> errors,
                                        HttpServletRequest request,
                                        Exception ex) {

        return ApiErrorResponse.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .errors(errors)
                .path(request.getRequestURI())
                .method(request.getMethod())
                .requestId(UUID.randomUUID().toString())
                .exception(ex != null ? ex.getClass().getName() : null)
                .cause(ex != null && ex.getCause() != null ? ex.getCause().getMessage() : null)
                .errorCode(null)      // future
                .helpUrl(null)        // future
                .userMessage(message) // same for now
                .build();
    }

    // 1. Validation errors (DTO annotations)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(err ->
                fieldErrors.put(err.getField(), err.getDefaultMessage())
        );

        ApiErrorResponse response = buildError(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                fieldErrors,
                request,
                ex
        );

        return ResponseEntity.badRequest().body(response);
    }

    // 2. IllegalArgumentException (your custom exceptions)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        ApiErrorResponse response = buildError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                null,
                request,
                ex
        );

        return ResponseEntity.badRequest().body(response);
    }

    // 3. Generic fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        ApiErrorResponse response = buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                null,
                request,
                ex
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthException(
            AuthException ex,
            HttpServletRequest request
    ) {
        ApiErrorResponse response = buildError(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),   // ← THIS is what you want
                null,
                request,
                ex
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }
}
