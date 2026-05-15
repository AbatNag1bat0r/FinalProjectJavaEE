package com.example.baglanulyabatfinalproject.exception;

import com.example.baglanulyabatfinalproject.dto.BaglanulyAbatApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class BaglanulyAbatGlobalExceptionHandler {

    @ExceptionHandler(BaglanulyAbatResourceNotFoundException.class)
    public ResponseEntity<BaglanulyAbatApiResponse<Void>> handleNotFound(
            BaglanulyAbatResourceNotFoundException ex) {
        log.error("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaglanulyAbatApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(BaglanulyAbatDuplicateResourceException.class)
    public ResponseEntity<BaglanulyAbatApiResponse<Void>> handleDuplicate(
            BaglanulyAbatDuplicateResourceException ex) {
        log.error("Duplicate resource: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(BaglanulyAbatApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(BaglanulyAbatBadRequestException.class)
    public ResponseEntity<BaglanulyAbatApiResponse<Void>> handleBadRequest(
            BaglanulyAbatBadRequestException ex) {
        log.error("Bad request: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaglanulyAbatApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaglanulyAbatApiResponse<Map<String, String>>> handleValidation(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        log.error("Validation failed: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaglanulyAbatApiResponse.<Map<String, String>>builder()
                        .success(false)
                        .message("Validation failed")
                        .data(errors)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaglanulyAbatApiResponse<Void>> handleGeneral(Exception ex) {
        log.error("Unexpected error: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaglanulyAbatApiResponse.error("Internal server error: " + ex.getMessage()));
    }
}