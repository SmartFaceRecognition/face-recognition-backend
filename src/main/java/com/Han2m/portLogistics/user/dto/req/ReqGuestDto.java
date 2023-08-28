package com.Han2m.portLogistics.user.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReqGuestDto{

    @NotBlank(message = "nationality은 필수값 입니다")
    private String nationality;
    @NotBlank(message = "name은 필수값 입니다")
    private String name;
    @NotNull(message = "sex은 필수값 입니다")
    private Boolean sex;
    @NotBlank(message = "birth은 필수값 입니다")
    @Size(min = 8,max = 8,message = "생년월일은 8자리 입니다") // ex. 19990101
    private String birth;
    @NotBlank(message = "phone은 필수값 입니다")
    @Size(min = 11,max = 11,message = "핸드폰 번호는 11자리 입니다") // ex. 01012345678
    private String phone;
    @NotNull(message = "date는 필수값 입니다")
    private LocalDate date;
    @NotBlank(message = "reason은 필수값 입니다")
    private String reason;
    @NotBlank(message = "goal은 필수값 입니다")
    private String goal;
    @NotNull(message = "workerId는 필수값 입니다")
    private Long workerId;
    @NotNull(message = "wharfs은 필수값 입니다")
    private List<String> wharfs;

}