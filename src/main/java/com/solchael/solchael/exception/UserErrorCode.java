package com.solchael.solchael.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    INACTIVE_USER(HttpStatus.FORBIDDEN, "User is inactive"),
    AUTH_USER(HttpStatus.UNAUTHORIZED, "세션이 만료되었거나 존재하지 않습니다. 다시 로그인해주세요")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
