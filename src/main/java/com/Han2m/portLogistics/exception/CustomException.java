package com.Han2m.portLogistics.exception;

import lombok.Getter;

import static com.Han2m.portLogistics.exception.ErrorCode.DUPLICATE_ID;
import static com.Han2m.portLogistics.exception.ErrorCode.ENTITY_NOT_FOUND;

@Getter
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;
    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public static class DuplicateIdException extends CustomException{
        public DuplicateIdException(){
            super(DUPLICATE_ID);
        }
    }

    public static class EntityNotFoundException extends CustomException{
        public EntityNotFoundException(){
            super(ENTITY_NOT_FOUND);
        }
    }


}
