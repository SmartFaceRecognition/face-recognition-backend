package com.Han2m.portLogistics.exception;

import org.springframework.http.HttpStatus;


//해당 id에 해당하는 리소스를 찾지 못했을때의 에러
public class EntityNotFoundException extends ApplicationException{

    @Override
    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }

    public EntityNotFoundException(){
        super("요청하신 리소스는 존재하지 않습니다");
    }
}
