package com.Han2m.portLogistics.exception.controller;

import com.Han2m.portLogistics.exception.ApiResponse;
import com.Han2m.portLogistics.exception.ApplicationException;
import com.Han2m.portLogistics.exception.CustomException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.Han2m.portLogistics.exception.ApiResponse.errorResponse;


@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(CustomException.class)
    public ApiResponse<?> handleCustomException(CustomException ex) {
        return errorResponse(ex.getErrorCode());
    }

    @ExceptionHandler(ApplicationException.class)
    public ApiResponse<?> handleApplicationException(ApplicationException ex) {
        return errorResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ApiResponse<?> handleExpiredJwtException(ExpiredJwtException ex) {
        return errorResponse(400,"유효기간이 만료된 토큰입니다.");
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ApiResponse<?> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        return errorResponse(400,"지원하지 않는 향식의 토큰입니다.");
    }

}
