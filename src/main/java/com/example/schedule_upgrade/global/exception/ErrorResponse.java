package com.example.schedule_upgrade.global.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    // Error Response 형태를 지정해주기
    // Frontend 에서 오류 출력 시 편해짐!
    private final LocalDateTime timestamp; // 언제?
    private final int status;              // HTTP 상태 코드 (400, 404 등)
    private final String error;            // 에러 이름 (BAD_REQUEST)
    private final String code;             // 우리가 정의한 코드 (M001)
    private final String message;          // 친절한 메시지
    private final String path;             // 요청 경로 (/api/auth/signup)
}