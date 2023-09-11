package com.Han2m.portLogistics.user.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReqWorkerDto {

    @NotBlank(message = "nationality은 필수값 입니다")
    private String nationality;
    @NotBlank(message = "name은 필수값 입니다")
    private String name;
    @NotNull(message = "sex은 필수값 입니다")
    private Boolean sex;
    @NotBlank(message = "company은 필수값 입니다")
    private String company;
    @NotBlank(message = "birth은 필수값 입니다")
    @Size(min = 10,max = 10,message = "생년월일은 8자리 입니다")
    private String birth;
    @NotBlank(message = "phone은 필수값 입니다")
    @Size(min =14,max = 14,message = "핸드폰 번호는 11자리 입니다")
    private String phone;
    @NotBlank(message = "position은 필수값 입니다")
    private String position;
    @NotNull(message = "wharfs은 필수값 입니다")
    private List<String> wharfs;
}
