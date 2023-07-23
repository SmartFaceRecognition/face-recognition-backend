package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.user.dto.GuestDto;
import com.Han2m.portLogistics.user.entity.Guest;
import com.Han2m.portLogistics.user.entity.GuestWharf;
import com.Han2m.portLogistics.user.entity.Wharf;
import com.Han2m.portLogistics.user.repository.GuestRepository;
import com.Han2m.portLogistics.user.repository.PersonRepository;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GuestService {


    private final GuestRepository guestRepository;
    private final WharfRepository wharfRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository, @Qualifier("wharfRepository") WharfRepository wharfRepository) {
        this.guestRepository = guestRepository;
        this.wharfRepository = wharfRepository;
    }


    // 손님 정보 조회
    public GuestDto getGuestById(Long id) {
        Optional<Guest> optionalGuest = guestRepository.findById(id);
        if (optionalGuest.isPresent()) {
            Guest guest = optionalGuest.get();
            GuestDto guestDto = convertToGuestDto(guest);

            List<Wharf> wharfList = wharfRepository.findByGuestWharfListGuestId(guestDto.getId());
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
    @Transactional
    public GuestDto registerGuest(GuestDto guestDto) {
        Guest guest = convertToGuestEntity(guestDto);
        Guest savedGuest = guestRepository.save(guest);

        if (guestDto.getWharfs() != null) {
            List<String> uniqueWharfs = guestDto.getWharfs().stream().distinct().collect(Collectors.toList());
            for (String wharf : uniqueWharfs) {
                if (!wharfRepository.existsByPlace(wharf)) {
                    Wharf newWharf = new Wharf();
                    newWharf.setPlace(wharf);
                    wharfRepository.save(newWharf);

                    GuestWharf guestWharf = new GuestWharf(savedGuest, newWharf);
                    savedGuest.getGuestWharfList().add(guestWharf);
                    newWharf.getGuestWharfList().add(guestWharf);
                }
            }
        }
        return convertToGuestDto(savedGuest);
    }

    @Transactional
    public GuestDto editGuestInfo(Long id, GuestDto updatedGuestDto) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 외부인이 없습니다."));
        guest.setName(updatedGuestDto.getName());
        guest.setBirth(updatedGuestDto.getBirth());
        guest.setPhone(updatedGuestDto.getPhone());
        guest.setSsn(updatedGuestDto.getSsn());
        guest.setAddress(updatedGuestDto.getAddress());
        guest.setSex(updatedGuestDto.getSex());

        List<GuestWharf> updatedGuestWharfs = new ArrayList<>();
        List<String> updatedWharfPlaces = updatedGuestDto.getWharfs();
        if (updatedWharfPlaces != null) {
            for (String place : updatedWharfPlaces) {
                List<Wharf> wharfEntities = wharfRepository.findByPlace(place);
                if (!wharfEntities.isEmpty()) {
                    Wharf wharfEntity = wharfEntities.get(0);
                    GuestWharf guestWharf = new GuestWharf(guest, wharfEntity);
                    updatedGuestWharfs.add(guestWharf);
                }
            }
        }
        guest.setGuestWharfList(updatedGuestWharfs);

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
