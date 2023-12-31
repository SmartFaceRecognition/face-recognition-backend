package com.Han2m.portLogistics.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.Han2m.portLogistics.exception.ResponseMsg.SUCCESS;

@Getter
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private boolean success;
    private String msg;
    private T result;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public static <T> ApiResponse<T> successResponse(T data) {
        return new ApiResponse<>(true,SUCCESS, data);
    }

    public static ApiResponse<?> successResponseNoContent() {
        return new ApiResponse<>(true, SUCCESS, null);
    }

    public static ApiResponse<?> errorResponse(String msg){
        return new ApiResponse<>(false,msg,null);
    }
}
