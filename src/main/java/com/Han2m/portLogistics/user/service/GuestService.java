package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.user.dto.req.ReqGuestDto;
import com.Han2m.portLogistics.user.dto.res.ResGuestDto;
import com.Han2m.portLogistics.user.entity.*;
import com.Han2m.portLogistics.user.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    //id 조회
    public boolean guestIsPresent(Long id){
        return guestRepository.findById(id).isEmpty();
    }

    public ResGuestDto registerGuest(ReqGuestDto reqGuestDto) {

        Guest guest = new Guest();
        guest.setNationality(reqGuestDto.getNationality());
        guest.setName(reqGuestDto.getName());
        guest.setSex(reqGuestDto.getSex());
        guest.setBirth(reqGuestDto.getBirth());
        guest.setPhone(reqGuestDto.getPhone());
        guest.setSsn(reqGuestDto.getSsn());

        guestRepository.save(guest);

        return new ResGuestDto(guest);
    }

    public ResGuestDto editGuestInfo(Long id, ReqGuestDto updatedReqGuestDTO) {

        Guest guest = guestRepository.findById(id).get();

        guest.setNationality(updatedReqGuestDTO.getNationality());
        guest.setName(updatedReqGuestDTO.getName());
        guest.setBirth(updatedReqGuestDTO.getBirth());
        guest.setSex(updatedReqGuestDTO.getSex());
        guest.setPhone(updatedReqGuestDTO.getPhone());
        guest.setSsn(updatedReqGuestDTO.getSsn());

        guestRepository.save(guest);

        return new ResGuestDto(guest);
    }

    public ResGuestDto getGuestById(Long id) {

        Guest guest = guestRepository.findById(id).get();

        return new ResGuestDto(guest);
    }

    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }


}
