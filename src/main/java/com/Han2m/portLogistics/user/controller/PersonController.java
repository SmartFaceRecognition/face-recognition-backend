package com.Han2m.portLogistics.user.controller;

import com.Han2m.portLogistics.user.dto.GuestDto;
import com.Han2m.portLogistics.user.dto.PersonDto;
import com.Han2m.portLogistics.user.dto.WorkerDto;
import com.Han2m.portLogistics.user.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;



    // Worker
    @GetMapping("/worker/{id}")
    public ResponseEntity<WorkerDto> getWorkerById(@PathVariable Long id) {
        WorkerDto workerDto = personService.getWorkerById(id);
        return ResponseEntity.ok(workerDto);
    }

    @PostMapping("/worker/register")
    public ResponseEntity<WorkerDto> registerWorker(@RequestBody WorkerDto workerDTO) {
        WorkerDto registeredWorker = personService.registerWorker(workerDTO);
        return ResponseEntity.ok(registeredWorker);
    }

    @PutMapping("/worker/{id}")
    public ResponseEntity<WorkerDto> updateWorker(@PathVariable Long id, @RequestBody WorkerDto updatedWorkerDTO) {
        WorkerDto updatedWorker = personService.editWorkerInfo(id, updatedWorkerDTO);
        return ResponseEntity.ok(updatedWorker);
    }

    @DeleteMapping("/worker/{id}")
    public ResponseEntity<String> deleteWorkerById(@PathVariable Long id) {
        personService.deletePerson(id);
        String responseMessage = id + "번 직원이 삭제되었습니다.";
        return ResponseEntity.ok(responseMessage);
    }





    // Guest
    @GetMapping("/guest/{id}")
    public ResponseEntity<GuestDto> getGuestById(@PathVariable Long id) {
        GuestDto guestDto = personService.getGuestById(id);
        return ResponseEntity.ok(guestDto);
    }

    @PostMapping("/guest/register")
    public ResponseEntity<GuestDto> registerGuest(@RequestBody GuestDto guestDTO) {
        GuestDto registeredGuest = personService.registerGuest(guestDTO);
        return ResponseEntity.ok(registeredGuest);
    }

    @PutMapping("/guest/{id}")
    public ResponseEntity<GuestDto> updateGuest(@PathVariable Long id, @RequestBody GuestDto updatedGuestDTO) {
        GuestDto updatedGuest = personService.editGuestInfo(id, updatedGuestDTO);
        return ResponseEntity.ok(updatedGuest);
    }

    @DeleteMapping("/guest/{id}")
    public ResponseEntity<String> deleteGuestById(@PathVariable Long id) {
        personService.deletePerson(id);
        String responseMessage = id + "번 손님이 삭제되었습니다.";
        return ResponseEntity.ok(responseMessage);
    }


    // 모든 사람 정보 조회 (페이징)
    @GetMapping("/person/access-history")
    public ResponseEntity<Page<PersonDto>> getAllPersons(
                    @RequestParam(defaultValue = "0") int page,
                    @RequestParam(defaultValue = "10") int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<PersonDto> persons = personService.getAllPersons(pageable);
        return ResponseEntity.ok(persons);
    }

    // 등록순으로 모든 사람 정보 조회 (페이징)
    @GetMapping("/person/orderByRegis")
    public ResponseEntity<Page<PersonDto>> getAllPersonsOrderByRegistrationDate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PersonDto> persons = personService.getAllPersonsOrderByRegistrationDate(pageable);
        return ResponseEntity.ok(persons);
    }


    // 이름으로 검색
    @GetMapping("/person/search")
    public ResponseEntity<List<PersonDto>> searchPersonsByName(
            @RequestParam String name
    ) {
        List<PersonDto> persons = personService.searchPersonByName(name);
        return ResponseEntity.ok(persons);
    }
}
