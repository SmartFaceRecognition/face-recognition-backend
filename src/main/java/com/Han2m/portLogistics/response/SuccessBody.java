package com.Han2m.portLogistics.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SuccessBody {

    private String msg = "성공하였습니다";

    private Object data;

    public SuccessBody(Object data) {
        this.data = data;
    }
}
