package com.Han2m.portLogistics.user.dto.res;

import com.Han2m.portLogistics.user.dto.PersonDto;
import com.Han2m.portLogistics.user.entity.Guest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResGuestDto extends PersonDto {

    private Long personId;
    private String ssn;

    public ResGuestDto(Guest guest) {
        super.setName(guest.getName());
        super.setSex(guest.getSex());
        super.setBirth(guest.getBirth());
        super.setPhone(guest.getPhone());
        super.setNationality(guest.getNationality());
        ssn = guest.getSsn();
        personId = guest.getPersonId();

    }
}