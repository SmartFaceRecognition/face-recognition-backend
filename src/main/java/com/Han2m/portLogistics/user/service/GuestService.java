package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.exception.EntityNotFoundException;
import com.Han2m.portLogistics.user.dto.req.ReqGuestDto;
import com.Han2m.portLogistics.user.dto.res.ResGuestDto;
import com.Han2m.portLogistics.user.entity.Guest;
import com.Han2m.portLogistics.user.entity.Worker;
import com.Han2m.portLogistics.user.repository.GuestRepository;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import com.Han2m.portLogistics.user.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final WharfRepository wharfRepository;
    private final WorkerRepository workerRepository;

    public ResGuestDto registerGuest(ReqGuestDto reqGuestDto) {

        Guest guest = reqGuestDto.toEntity();

        //부두 등록
        guest.setWharfList(reqGuestDto.getWharfs().stream().map(wharfRepository::findByName).collect(Collectors.toList()));

        //담당자 등록
        Worker worker = workerRepository.findById(reqGuestDto.getWorkerId()).orElseThrow(EntityNotFoundException::new);
        guest.setWorker(worker);

        guestRepository.save(guest);

        return guest.toResGuestDto();
    }

    public ResGuestDto editGuestInfo(Long id, ReqGuestDto reqGuestDto) {

        Guest guest = guestRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        //Guest의 정보 수정
        if(reqGuestDto.getWharfs() != null) {
            guest.setWharfList(reqGuestDto.getWharfs().stream().map(wharfRepository::findByName).collect(Collectors.toList()));
        }
        guest.updateGuest(reqGuestDto);

        return guest.toResGuestDto();
    }

    @Transactional(readOnly = true)
    public ResGuestDto getGuestById(Long id) {

        Guest guest = guestRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return guest.toResGuestDto();
    }

    public void deleteGuest(Long personId) {
         guestRepository.delete(guestRepository.findById(personId).orElseThrow(EntityNotFoundException::new));
    }

//    @Transactional(readOnly = true)
//    public Page<ResGuestDto> getAllGuests(Pageable pageable) {
//        Page<Guest> guests = guestRepository.findAll(pageable);
//        return guests.map(ResGuestDto::new);
//    }
//
//    @Transactional(readOnly = true)
//    public List<ResGuestDto> searchGuestByName(String name) {
//        List<Guest> guests = guestRepository.findByName(name);
//        return guests.stream().map(ResGuestDto::new).collect(Collectors.toList());
//    }


}
