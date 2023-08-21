package com.Han2m.portLogistics.user.dto.res;

import com.Han2m.portLogistics.user.entity.Guest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResGuestDto{

    private String nationality;
    private String name;
    private Boolean sex;
    private String birth;
    private String phone;
    private Long personId;
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
        this.workerId = guest.getWorker().getPersonId();
        this.wharfs = guest.getWharfPlaces();
    }

}