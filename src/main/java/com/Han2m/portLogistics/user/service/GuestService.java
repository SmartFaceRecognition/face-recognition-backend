package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.exception.CustomException;
import com.Han2m.portLogistics.user.domain.Guest;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.domain.Worker;
import com.Han2m.portLogistics.user.dto.req.ReqGuestDto;
import com.Han2m.portLogistics.user.repository.GuestRepository;
import com.Han2m.portLogistics.user.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final WharfService wharfService;
    private final WorkerRepository workerRepository;
    private final PermissionService permissionService;

    public Guest find(Long personId) {
        return guestRepository.findById(personId).orElseThrow(CustomException.EntityNotFoundException::new);
    }


    public List<Guest> findAllGuestAndWharf(String name,int sort,int dir){
        Sort.Direction direction = (dir == 1) ? Sort.Direction.ASC : Sort.Direction.DESC;

        String sortField = (sort == 0) ? "name" : "position";
        Sort dynamicSort = Sort.by(direction, sortField);

        return guestRepository.findAllGuestWithWharf(name,dynamicSort);
    }

    public Guest registerGuest(ReqGuestDto reqGuestDto) {

        List<Wharf> wharfList = wharfService.getWharfList(reqGuestDto.getWharfs());

        Guest guest = reqGuestDto.toEntity();

        permissionService.permit(guest,wharfList);

        //담당자 등록
        Worker worker = workerRepository.findById(reqGuestDto.getWorkerId()).orElseThrow(CustomException.EntityNotFoundException::new);
        guest.setWorker(worker);

        guestRepository.save(guest);

        return guest;
    }


    public void editGuestInfo(Long personId, ReqGuestDto reqGuestDto) {

        Guest guest = find(personId);
        guest.updateGuest(reqGuestDto);

    }

    public void deleteGuest(Long personId) {
        guestRepository.delete(guestRepository.findById(personId).orElseThrow(CustomException.EntityNotFoundException::new));
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
