package com.Han2m.portLogistics.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ErrorBody {

    private int status;
    private String msg;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ErrorBody(ApplicationException applicationException) {
        this(applicationException.status().value(), applicationException.getMessage());
    }

}
