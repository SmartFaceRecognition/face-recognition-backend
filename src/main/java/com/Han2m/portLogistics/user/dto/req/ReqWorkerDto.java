package com.Han2m.portLogistics.user.dto.req;

import com.Han2m.portLogistics.user.entity.Worker;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReqWorkerDto {

    @NotBlank(groups = {CreateGroup.class}, message = "nationality은 필수값 입니다")
    private String nationality;
    @NotBlank(groups = {CreateGroup.class},message = "name은 필수값 입니다")
    private String name;
    @NotNull(groups = {CreateGroup.class},message = "sex은 필수값 입니다")
    private Boolean sex;
    @NotBlank(groups = {CreateGroup.class},message = "birth은 필수값 입니다")
    @Size(groups = {CreateGroup.class},min = 10,max = 10,message = "생년월일은 8자리 입니다")
    private String birth;
    @NotBlank(groups = {CreateGroup.class},message = "phone은 필수값 입니다")
    @Size(groups = {CreateGroup.class},min =13,max = 13,message = "핸드폰 번호는 11자리 입니다")
    private String phone;
    @NotBlank(groups = {CreateGroup.class},message = "position은 필수값 입니다")
    private String position;

    //wharfs name이 들어온다
    @NotNull(groups = {CreateGroup.class},message = "wharfs은 필수값 입니다")
    private List<String> wharfs;

    public Worker toEntity(){
         return Worker.builder().
                 nationality(nationality).
                 name(name).
                 position(position).
                 birth(birth).
                 sex(sex).
                 phone(phone).
                 build();
    }

}
