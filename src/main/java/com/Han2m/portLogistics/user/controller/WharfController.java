//package com.Han2m.portLogistics.user.controller;
//
//import com.Han2m.portLogistics.user.dto.req.GuestDto;
//import com.Han2m.portLogistics.user.dto.PersonDto;
//import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
//import com.Han2m.portLogistics.user.service.WharfService;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//public class WharfController {
//
//    private final WharfService wharfService;
//
//
//
//    // 해당 부두에 몇명이 있는지 조회
//    @GetMapping("/wharf/num-person/{wharfName}")
//    public ResponseEntity<Integer> getNumberOfPersonsAtWharf(@PathVariable String wharfName) {
//        Integer numPersons = wharfService.getTotalPersonByWharfName(wharfName);
//        return ResponseEntity.ok(numPersons);
//    }
//
//
//    // 부두별 직원 조회
//    @GetMapping("/wharf/current-worker/{wharfName}")
//    public ResponseEntity<List<ReqWorkerDto>> getCurrentEntrantsByWharf(@PathVariable String wharfName) {
//        List<ReqWorkerDto> currentPerson = wharfService.getCurrentWorkerByWharf(wharfName);
//        return ResponseEntity.ok(currentPerson);
//    }
//
//
//}
