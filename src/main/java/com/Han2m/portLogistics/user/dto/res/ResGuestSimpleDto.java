package com.Han2m.portLogistics.user.dto.res;

import com.Han2m.portLogistics.user.domain.Guest;
import com.Han2m.portLogistics.user.domain.Permission;
import com.Han2m.portLogistics.user.domain.Wharf;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ResGuestSimpleDto {
    private Long guestId;
    private String nationality;
    private String name;
    private Boolean sex;
    private LocalDate date;
    private List<String> wharfs;

    public ResGuestSimpleDto(Guest guest) {
        this.guestId = guest.getPersonId();
        this.nationality = guest.getNationality();
        this.name = guest.getName();
        this.sex = guest.getSex();
        this.date = guest.getDate();
        this.wharfs = guest.getPermissionList().stream().map(Permission::getWharf).map(Wharf::getName).toList();
    }
}
