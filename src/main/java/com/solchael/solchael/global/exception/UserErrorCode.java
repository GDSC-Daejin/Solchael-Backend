package com.solchael.solchael.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    INACTIVE_USER(HttpStatus.FORBIDDEN, "User is inactive"),
    AUTH_USER(HttpStatus.UNAUTHORIZED, "세션이 만료되었거나 존재하지 않습니다. 다시 로그인해주세요"),
    CONFLICT_NAME(HttpStatus.CONFLICT, "중복된 닉네임입니다."),
    CONFLICT_EMAIL(HttpStatus.CONFLICT, "중복된 아이디입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
