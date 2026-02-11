package com.example.schedule_upgrade.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedUserException extends SystemException {
    public UnauthorizedUserException() {
        super(HttpStatus.UNAUTHORIZED, "인가되지 않은 사용자입니다.");
    }
}
