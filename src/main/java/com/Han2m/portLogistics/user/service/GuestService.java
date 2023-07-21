package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.user.dto.GuestDto;
import com.Han2m.portLogistics.user.dto.PersonDto;
import com.Han2m.portLogistics.user.entity.Guest;
import com.Han2m.portLogistics.user.entity.Person;
import com.Han2m.portLogistics.user.repository.GuestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    // 손님 정보 조회
    public GuestDto getGuestById(Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("외부인을 찾을 수 없습니다. ID: " + id));
        return convertToGuestDto(guest);
    }

    // 손님 등록
    public GuestDto registerGuest(GuestDto guestDto) {
        Guest guest = convertToGuestEntity(guestDto);
        Guest savedGuest = guestRepository.save(guest);
        return convertToGuestDto(savedGuest);
    }

    // 손님 정보 수정
    public GuestDto editGuestInfo(Long id, GuestDto updatedGuestDto) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("외부인을 찾을 수 없습니다. ID: " + id));
        guest.setName(updatedGuestDto.getName());
        guest.setBirth(updatedGuestDto.getBirth());
        guest.setPhone(updatedGuestDto.getPhone());
        guest.setSsn(updatedGuestDto.getSsn());
        guest.setAddress(updatedGuestDto.getAddress());
        guest.setSex(updatedGuestDto.getSex());
        Guest updatedGuest = guestRepository.save(guest);
        return convertToGuestDto(updatedGuest);
    }

    // 손님 삭제
    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }



    // 이름으로 손님 찾기
    public List<GuestDto> searchGuestByName(String name) {
        List<Guest> guests = guestRepository.findByName(name);
        return guests.stream()
                .map(this::convertToGuestDto)
                .collect(Collectors.toList());
    }


    private Guest convertToGuestEntity(GuestDto guestDto) {
        Guest guest = new Guest();
        guest.setName(guestDto.getName());
        guest.setBirth(guestDto.getBirth());
        guest.setPhone(guestDto.getPhone());
        guest.setSsn(guestDto.getSsn());
        guest.setAddress(guestDto.getAddress());
        guest.setSex(guestDto.getSex());
        return guest;
    }

    private GuestDto convertToGuestDto(Guest guest) {
        GuestDto guestDto = new GuestDto();
        guestDto.setId(guest.getId());
        guestDto.setName(guest.getName());
        guestDto.setBirth(guest.getBirth());
        guestDto.setPhone(guest.getPhone());
        guestDto.setSsn(guest.getSsn());
        guestDto.setAddress(guest.getAddress());
        guestDto.setSex(guest.getSex());
        return guestDto;
    }
}
