package com.example.baglanulyabatfinalproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BaglanulyAbatResourceNotFoundException extends RuntimeException {

    public BaglanulyAbatResourceNotFoundException(String message) {
        super(message);
    }

    public BaglanulyAbatResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " not found with id: " + id);
    }
}