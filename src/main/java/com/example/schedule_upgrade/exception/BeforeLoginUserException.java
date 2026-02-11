package com.example.schedule_upgrade.exception;

import org.springframework.http.HttpStatus;

public class BeforeLoginUserException extends SystemException {
    public BeforeLoginUserException() {
        super(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
    }
}
