package com.Han2m.portLogistics.user.controller;

import com.Han2m.portLogistics.user.dto.PersonDto;
import com.Han2m.portLogistics.user.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/person/{id}")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable Long id) {
        PersonDto personDto = personService.getPersonById(id);
        return ResponseEntity.ok(personDto);
    }

    // 오류
    // {
    //    "nationality":"한국",
    //    "name":"전광휘",
    //    "birth":"19991105",
    //    "phone":"01058446289",
    //    "position":"leader",
    //    "faceUrl":"https://port-entrance.s3.ap-northeast-2.amazonaws.com/Envv.png",
    //    "wharfs": ["제 1부두", "제 2부두"]
    //}
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
}
