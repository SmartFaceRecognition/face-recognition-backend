package com.Han2m.portLogistics.user.dto.res;

import com.Han2m.portLogistics.user.domain.Guest;
import com.Han2m.portLogistics.user.domain.PersonWharf;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResGuestDto{

    private String nationality;
    private String name;
    private Boolean sex;
    private String birth;
    private String phone;
    private Long personId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String reason;
    private String goal;
    private Long workerId;
    private List<String> wharfs;


    public ResGuestDto(Guest guest) {
        this.nationality = guest.getNationality();
        this.name = guest.getName();
        this.sex = guest.getSex();
        this.birth = guest.getBirth();
        this.phone = guest.getPhone();
        this.personId = guest.getPersonId();
        this.date = guest.getDate();
        this.reason = guest.getReason();
        this.goal = guest.getGoal();
        this.workerId = guest.getPersonId();
        this.wharfs = guest.getPersonWharfList().stream().map(PersonWharf::getWharf).map(Wharf::getName).toList();
    }
}