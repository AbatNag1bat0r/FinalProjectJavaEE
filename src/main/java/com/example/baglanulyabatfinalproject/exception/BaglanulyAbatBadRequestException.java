package com.example.baglanulyabatfinalproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BaglanulyAbatBadRequestException extends RuntimeException {

    public BaglanulyAbatBadRequestException(String message) {
        super(message);
    }
}