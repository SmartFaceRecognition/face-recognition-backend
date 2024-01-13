package com.Han2m.portLogistics.exception.controller;

import com.Han2m.portLogistics.exception.ApiResponse;
import com.Han2m.portLogistics.exception.ApplicationException;
import com.Han2m.portLogistics.exception.CustomException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.Han2m.portLogistics.exception.ApiResponse.errorResponse;


@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(CustomException.class)
    public ApiResponse<?> handleCustomException(CustomException ex) {
        return errorResponse(ex.getErrorApiResponse());
    }

    @ExceptionHandler(ApplicationException.class)
    public ApiResponse<?> handleApplicationException(ApplicationException ex) {
        return errorResponse(ex.getStatus(), ex.getMessage());
    }
}
