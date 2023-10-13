package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.exception.EntityNotFoundException;
import com.Han2m.portLogistics.user.dto.req.ReqGuestDto;
import com.Han2m.portLogistics.user.dto.res.ResGuestDto;
import com.Han2m.portLogistics.user.entity.Guest;
import com.Han2m.portLogistics.user.entity.PersonWharf;
import com.Han2m.portLogistics.user.entity.Wharf;
import com.Han2m.portLogistics.user.entity.Worker;
import com.Han2m.portLogistics.user.repository.GuestRepository;
import com.Han2m.portLogistics.user.repository.PersonWharfRepository;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import com.Han2m.portLogistics.user.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final WharfRepository wharfRepository;
    private final PersonWharfRepository personWharfRepository;
    private final WorkerRepository workerRepository;

    public ResGuestDto registerGuest(ReqGuestDto reqGuestDto) {

        Guest guest = new Guest();
        guest.setNationality(reqGuestDto.getNationality());
        guest.setName(reqGuestDto.getName());
        guest.setSex(reqGuestDto.getSex());
        guest.setBirth(reqGuestDto.getBirth());
        guest.setPhone(reqGuestDto.getPhone());
        guest.setGoal(reqGuestDto.getGoal());
        guest.setReason(reqGuestDto.getReason());

        //담당자 등록
        Worker worker = workerRepository.findById(reqGuestDto.getWorkerId()).orElseThrow(EntityNotFoundException::new);
        guest.setWorker(worker);

        //부두 등록
        List<PersonWharf> personWharves = guest.getPersonWharfList();

        for (String place : reqGuestDto.getWharfs()) {
            Wharf wharf = wharfRepository.findByPlace(place).get(0);
            personWharves.add(new PersonWharf(guest, wharf));
        }

        guestRepository.save(guest);

        return new ResGuestDto(guest);
    }

    public ResGuestDto editGuestInfo(Long id, ReqGuestDto updatedReqGuestDTO) {

        Guest guest = guestRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        guest.setNationality(updatedReqGuestDTO.getNationality());
        guest.setName(updatedReqGuestDTO.getName());
        guest.setBirth(updatedReqGuestDTO.getBirth());
        guest.setSex(updatedReqGuestDTO.getSex());
        guest.setPhone(updatedReqGuestDTO.getPhone());

        //기존 정보 삭제
        for (PersonWharf PersonWharf : guest.getPersonWharfList()) {
            personWharfRepository.deleteById(PersonWharf.getPersonWharfId());
        }
        guest.setPersonWharfList(new ArrayList<>());

        List<PersonWharf> personWharves = guest.getPersonWharfList();

        for (String place : updatedReqGuestDTO.getWharfs()) {
            Wharf wharf = wharfRepository.findByPlace(place).get(0);
            personWharves.add(new PersonWharf(guest, wharf));
        }

        guest.setPersonWharfList(personWharves);

        guestRepository.save(guest);

        return new ResGuestDto(guest);
    }

    @Transactional(readOnly = true)
    public ResGuestDto getGuestById(Long id) {

        Guest guest = guestRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return new ResGuestDto(guest);
    }

    public void deleteGuest(Long id) {
         guestRepository.findById(id).map(guest -> {
             guestRepository.delete(guest);
             return guest;
         }).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<ResGuestDto> getAllGuests(Pageable pageable) {
        Page<Guest> guests = guestRepository.findAll(pageable);
        return guests.map(ResGuestDto::new);
    }

    @Transactional(readOnly = true)
    public List<ResGuestDto> searchGuestByName(String name) {
        List<Guest> guests = guestRepository.findByName(name);
        return guests.stream().map(ResGuestDto::new).collect(Collectors.toList());
    }


}
