package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.exception.EntityNotFoundException;
import com.Han2m.portLogistics.user.domain.Guest;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.domain.Worker;
import com.Han2m.portLogistics.user.dto.req.ReqGuestDto;
import com.Han2m.portLogistics.user.repository.GuestRepository;
import com.Han2m.portLogistics.user.repository.WharfRepository;
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
    private final WorkerService workerService;
    private final PersonWharfService personWharfService;

    @Transactional(readOnly = true)
    public Guest find(Long id) {
        return guestRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteGuest(Long personId) {
        guestRepository.delete(guestRepository.findById(personId).orElseThrow(EntityNotFoundException::new));
    }

    public Guest registerGuest(ReqGuestDto reqGuestDto) {

        List<Wharf> wharfList = reqGuestDto.getWharfs().stream().map(wharfRepository::findById).filter(Optional::isPresent).map(Optional::get).toList();

        Guest guest = reqGuestDto.toEntity();

        personWharfService.matchPersonWharf(guest,wharfList);

        //담당자 등록
        Worker worker = workerService.find(reqGuestDto.getWorkerId());
        guest.setWorker(worker);

        return guest;
    }


    public Guest editGuestInfo(Long id, ReqGuestDto reqGuestDto) {

        Guest guest = find(id);
        guest.updateGuest(reqGuestDto);

        return guest;
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
