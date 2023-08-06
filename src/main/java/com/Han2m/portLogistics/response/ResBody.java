package com.Han2m.portLogistics.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResBody {
    public static ResponseEntity<Object> successResponse() {
        SuccessBody successBody = new SuccessBody();
        return ResponseEntity.ok(successBody);
    }

    public static ResponseEntity<Object> successResponse(Object data) {
        SuccessBody successBody = new SuccessBody(data);
        return ResponseEntity.ok(successBody);
    }

    public static ResponseEntity<Object> notFoundResponse(String message) {
        ErrorBody errorBody = new ErrorBody(message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }
}
