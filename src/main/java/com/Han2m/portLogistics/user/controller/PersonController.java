package com.Han2m.portLogistics.user.controller;

import com.Han2m.portLogistics.user.dto.PersonDto;
import com.Han2m.portLogistics.user.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/person/{id}")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable Long id) {
        PersonDto personDto = personService.getPersonById(id);
        return ResponseEntity.ok(personDto);
    }

    @PostMapping("/person/register")
    public ResponseEntity<PersonDto> registerPerson(@RequestBody PersonDto personDTO) {
        PersonDto registeredPerson = personService.registerPerson(personDTO);
        return ResponseEntity.ok(registeredPerson);
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable Long id, @RequestBody PersonDto updatedPersonDTO) {
        PersonDto updatedPerson = personService.editPersonInfo(id, updatedPersonDTO);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<String> deletePersonById(@PathVariable Long id) {
        personService.deletePerson(id);
        String responseMessage = id + "이(가) 삭제되었습니다.";
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

    // 직원, 손님만 따로 조회하는 기능도 추가해야
    // ㄴ 이거는 직원과 손님을 구분한 뒤에 ㄱㄱ

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
        List<PersonDto> persons = personService.searchPersonsByName(name);
        return ResponseEntity.ok(persons);
    }
}
