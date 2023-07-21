package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.user.dto.GuestDto;
import com.Han2m.portLogistics.user.dto.PersonDto;
import com.Han2m.portLogistics.user.entity.Guest;
import com.Han2m.portLogistics.user.entity.Person;
import com.Han2m.portLogistics.user.entity.Wharf;
import com.Han2m.portLogistics.user.repository.GuestRepository;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GuestService {

    private final GuestRepository guestRepository;
    private final WharfRepository wharfRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository,
                        WharfRepository wharfRepository) {
        this.guestRepository = guestRepository;
        this.wharfRepository = wharfRepository;
    }

    // 손님 정보 조회
    public GuestDto getPersonById(Long id) {
        Optional<Guest> optionalPerson = guestRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Guest guest = optionalPerson.get();
            GuestDto guestDto = convertToGuestDto(guest);

            List<Wharf> wharfList = wharfRepository.findByGuestName(guest.getName());
            List<String> wharfs = wharfList.stream()
                    .map(Wharf::getPlace)
                    .collect(Collectors.toList());
            guestDto.setWharfs(wharfs);

            return guestDto;
        } else {
            throw new EntityNotFoundException("해당 손님을 찾을 수 없습니다. ID : " + id);
        }
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
                .orElseThrow(() -> new EntityNotFoundException("해당 손님을 찾을 수 없습니다. ID: " + id));
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
