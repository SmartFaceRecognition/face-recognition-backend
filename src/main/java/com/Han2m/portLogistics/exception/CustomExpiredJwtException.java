package com.Han2m.portLogistics.exception;

public class CustomExpiredJwtException extends RuntimeException {
    public CustomExpiredJwtException(String msg) {
        super(msg);
    }
}