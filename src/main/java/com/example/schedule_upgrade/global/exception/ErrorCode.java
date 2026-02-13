package com.example.schedule_upgrade.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // user 관련 에러
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "M001", "중복된 이메일 입니다."),
    WRONG_PW(HttpStatus.UNAUTHORIZED, "M002", "비밀번호가 일치하지 않습니다."),
    BEFORE_LOGIN(HttpStatus.UNAUTHORIZED, "M003", "로그인이 필요합니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "M004", "존재하지 않는 사용자입니다."),
    WRITER_MISMATCH(HttpStatus.FORBIDDEN, "M005", "작성자만 접근할 수 있습니다."),
    USER_MISMATCH(HttpStatus.FORBIDDEN, "M006", "유저 본인이 아닙니다."),

    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "S001", "존재하지 않는 일정입니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "S002", "존재하지 않는 댓글입니다."),

    //공통에러는 C 로 시작
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력값입니다,"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C002", "서버 내부 오류가 발생했습니다.");

    // 상태코드, 내가 지정한 오류코드, 출력할 메시지
    private final HttpStatus status;
    private final String code;
    private final String message;
}
