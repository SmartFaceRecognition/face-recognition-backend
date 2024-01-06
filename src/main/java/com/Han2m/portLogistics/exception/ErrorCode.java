package com.Han2m.portLogistics.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    ENTITY_NOT_FOUND(404, "요청하신 리소스를 찾을 수 없습니다"),
    DUPLICATE_ID(400, "해당 아이디는 이미 존재합니다");

    private final int status;

    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}