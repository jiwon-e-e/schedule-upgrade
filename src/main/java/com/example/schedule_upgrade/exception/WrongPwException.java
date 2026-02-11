package com.example.schedule_upgrade.exception;

import org.springframework.http.HttpStatus;

public class WrongPwException extends SystemException {
    public WrongPwException() {
        super(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 틀렸습니다.");
    }
}
