package com.Han2m.portLogistics.user.dto.req;

import com.Han2m.portLogistics.user.entity.Guest;
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

    @NotBlank(groups = {CreateGroup.class},message = "nationality은 필수값 입니다")
    private String nationality;
    @NotBlank(groups = {CreateGroup.class},message = "name은 필수값 입니다")
    private String name;
    @NotNull(groups = {CreateGroup.class},message = "sex은 필수값 입니다")
    private Boolean sex;
    @NotBlank(groups = {CreateGroup.class},message = "birth은 필수값 입니다")
    @Size(min = 11,max = 11,message = "생년월일은 8자리 입니다") // ex. 19990101
    private String birth;
    @NotBlank(groups = {CreateGroup.class},message = "phone은 필수값 입니다")
    @Size(min = 14,max = 14,message = "핸드폰 번호는 11자리 입니다") // ex. 01012345678
    private String phone;
    @NotNull(groups = {CreateGroup.class},message = "date는 필수값 입니다")
    private LocalDate date;
    @NotBlank(groups = {CreateGroup.class},message = "reason은 필수값 입니다")
    private String reason;
    @NotBlank(groups = {CreateGroup.class},message = "goal은 필수값 입니다")
    private String goal;
    @NotNull(groups = {CreateGroup.class},message = "workerId는 필수값 입니다")
    private Long workerId;
    @NotNull(groups = {CreateGroup.class},message = "wharfs은 필수값 입니다")
    private List<String> wharfs;

    public Guest toEntity(){
        return  Guest.builder().
                nationality(nationality).
                name(name).
                birth(birth).
                sex(sex).
                phone(phone).
                reason(reason).
                goal(goal).
                date(date).
                build();
    }

}