package com.example.schedule_upgrade.exception;

import org.springframework.http.HttpStatus;

public class NonExistentException extends SystemException {
    public NonExistentException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
