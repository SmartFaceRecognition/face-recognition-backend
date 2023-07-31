package com.Han2m.portLogistics.user.controller;

import com.Han2m.portLogistics.user.dto.GuestDto;
import com.Han2m.portLogistics.user.dto.PersonDto;
import com.Han2m.portLogistics.user.dto.WorkerDto;
import com.Han2m.portLogistics.user.service.WharfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WharfController {

    private final WharfService wharfService;

    @Autowired
    public WharfController(WharfService wharfService) {
        this.wharfService = wharfService;
    }

    // 해당 부두에 있는 모든 사람 다 조회
    @GetMapping("/wharf/person/{wharfName}")
    public ResponseEntity<List<PersonDto>> getAllPersonsByWharfName(@PathVariable String wharfName) {
        List<PersonDto> persons = wharfService.getCurrentPersonsByWharf(wharfName);
        return ResponseEntity.ok(persons);
    }


    // 해당 부두에 몇명이 있는지 조회
    @GetMapping("/wharf/num-person/{wharfName}")
    public ResponseEntity<Integer> getNumberOfPersonsAtWharf(@PathVariable String wharfName) {
        Integer numPersons = wharfService.getTotalPersonByWharfName(wharfName);
        return ResponseEntity.ok(numPersons);
    }


    // 부두별 직원 조회
    @GetMapping("/wharf/current-person/{wharfName}")
    public ResponseEntity<List<WorkerDto>> getCurrentEntrantsByWharf(@PathVariable String wharfName) {
        List<WorkerDto> currentPerson = wharfService.getCurrentWorkerByWharf(wharfName);
        return ResponseEntity.ok(currentPerson);
    }

    // 부두별 손님 조회
    @GetMapping("/wharf/current-guest/{wharfName}")
    public ResponseEntity<List<GuestDto>> getCurrentGuestByWharf(@PathVariable String wharfName) {
        List<GuestDto> currentGuest = wharfService.getCurrentGuestByWharf(wharfName);
        return ResponseEntity.ok(currentGuest);
    }

}
