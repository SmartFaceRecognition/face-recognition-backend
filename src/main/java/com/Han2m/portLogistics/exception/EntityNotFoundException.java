package com.Han2m.portLogistics.exception;

import static com.Han2m.portLogistics.exception.ResponseMsg.ENTITY_NOT_FOUND;

//해당 id에 해당하는 리소스를 찾지 못했을때의 에러
public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(){
        super(ENTITY_NOT_FOUND);
    }
}
