package com.Han2m.portLogistics.exception;

public enum ErrorType {
    DUPLICATE_ID(400,"중복되는 ID가 존재합니다"),
    JWT_UN_SUPPORTED(401,"지원하지 않는 형식의 토큰입니다"),
    JWT_EXPIRED(401,"토큰 유효기간이 지났습니다"),
    ACCESS_DENIED(403,"접근 권한이 없습니다");
    final int status;
    final String msg;

    ErrorType(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
