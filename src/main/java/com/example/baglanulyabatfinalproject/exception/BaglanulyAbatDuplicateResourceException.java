package com.example.baglanulyabatfinalproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BaglanulyAbatDuplicateResourceException extends RuntimeException {

    public BaglanulyAbatDuplicateResourceException(String message) {
        super(message);
    }
}