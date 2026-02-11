package com.example.schedule_upgrade.exception;

import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends SystemException {
    public DuplicateEmailException() {
        super(HttpStatus.BAD_REQUEST, "중복된 이메일입니다.");
    }
}
