package com.Han2m.portLogistics.exception.controller;

import com.Han2m.portLogistics.exception.ApiResponse;
import com.Han2m.portLogistics.exception.EntityNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.Han2m.portLogistics.exception.ApiResponse.errorResponse;


@RestControllerAdvice
public class ExceptionHandlerController {

    //유효성 검사에서의 오류
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ApiResponse<?> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//
//        //가장위에 오류 유효성 검사에서의 오류를 가져온다
//        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
//
//        errorResponse(ENTITY_NOT_FOUND);
//
//        return .;
//    }

    //요청한 pk로 엔티티를 찾을수없을때의 오류
    @ExceptionHandler(com.Han2m.portLogistics.exception.EntityNotFoundException.class)
    public ApiResponse<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return errorResponse(ex.getMessage());
    }


}
