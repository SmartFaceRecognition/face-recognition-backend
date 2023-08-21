package com.Han2m.portLogistics.exception.Controller;

import com.Han2m.portLogistics.exception.EntityNotFoundException;
import com.Han2m.portLogistics.exception.ErrorBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionHandlerController {

    //유효성 검사에서의 오류
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorBody> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        //가장위에 오류 유효성 검사에서의 오류를 가져온다
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        ErrorBody errorBody = ErrorBody.builder()
                .msg(errorMessage)
                .status(HttpStatus.BAD_REQUEST.value()).build();
        return ResponseEntity.status(ex.getStatusCode()).body(errorBody);
    }

    //요청한 pk로 엔티티를 찾을수없을때의 오류
    @ExceptionHandler(com.Han2m.portLogistics.exception.EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorBody> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorBody(ex));
    }


}
