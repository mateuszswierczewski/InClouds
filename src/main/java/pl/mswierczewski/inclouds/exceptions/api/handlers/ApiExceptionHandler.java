package pl.mswierczewski.inclouds.exceptions.api.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.mswierczewski.inclouds.dtos.error.ApiErrorResponse;
import pl.mswierczewski.inclouds.dtos.error.ApiValidationResponse;
import pl.mswierczewski.inclouds.exceptions.api.ApiException;


import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException apiException) {
        ApiErrorResponse response = new ApiErrorResponse(
                apiException.getClass().toString(),
                apiException.getMessage(),
                apiException.getStatus()
        );

        return ResponseEntity
                .status(apiException.getStatus())
                .body(response);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException validationException) {
        Map<String, String> errors = new HashMap<>();

        validationException.getBindingResult().getAllErrors().forEach(
                error -> {
                    String field = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    errors.put(field, message);
                }
        );

        ApiValidationResponse response = new ApiValidationResponse(
                validationException.getClass().toString(),
                "Validation failed!",
                HttpStatus.BAD_REQUEST,
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

}
