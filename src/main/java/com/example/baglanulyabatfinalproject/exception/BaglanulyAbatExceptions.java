package com.example.baglanulyabatfinalproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class BaglanulyAbatResourceNotFoundException extends RuntimeException {
    public BaglanulyAbatResourceNotFoundException(String message) {
        super(message);
    }
    public BaglanulyAbatResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " not found with id: " + id);
    }
}

@ResponseStatus(HttpStatus.CONFLICT)
class BaglanulyAbatDuplicateResourceException extends RuntimeException {
    public BaglanulyAbatDuplicateResourceException(String message) {
        super(message);
    }
}

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BaglanulyAbatBadRequestException extends RuntimeException {
    public BaglanulyAbatBadRequestException(String message) {
        super(message);
    }
}