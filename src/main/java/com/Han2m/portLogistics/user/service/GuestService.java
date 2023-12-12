package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.exception.EntityNotFoundException;
import com.Han2m.portLogistics.user.domain.Guest;
import com.Han2m.portLogistics.user.domain.PersonWharf;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.domain.Worker;
import com.Han2m.portLogistics.user.dto.req.ReqGuestDto;
import com.Han2m.portLogistics.user.dto.res.ResGuestDto;
import com.Han2m.portLogistics.user.repository.GuestRepository;
import com.Han2m.portLogistics.user.repository.PersonWharfRepository;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import com.Han2m.portLogistics.user.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final WharfRepository wharfRepository;
    private final WorkerRepository workerRepository;
    private final PersonWharfRepository personWharfRepository;

    public ResGuestDto registerGuest(ReqGuestDto reqGuestDto) {

        List<Wharf> wharfList = reqGuestDto.getWharfs().stream().map(wharfRepository::findById).filter(Optional::isPresent).map(Optional::get).toList();

        Guest guest = reqGuestDto.toEntity();

        for(Wharf wharf:wharfList){
            PersonWharf personWharf = new PersonWharf(guest,wharf);
            guest.getPersonWharfList().add(personWharf);
        }

        //담당자 등록
        Worker worker = workerRepository.findById(reqGuestDto.getWorkerId()).orElseThrow(EntityNotFoundException::new);
        guest.setWorker(worker);

        //부두 등록


        return guest.toResGuestDto();
    }


    public ResGuestDto editGuestInfo(Long id, ReqGuestDto reqGuestDto) {

        Guest guest = guestRepository.findById(id).orElseThrow(EntityNotFoundException::new);
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
