//package com.Han2m.portLogistics.user.controller;
//
//import com.Han2m.portLogistics.user.entity.Guest;
//import com.Han2m.portLogistics.user.entity.Person;
//import com.Han2m.portLogistics.user.entity.Status;
//import com.Han2m.portLogistics.user.service.WharfService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class WharfController {
//
//    private final WharfService wharfService;
//
//    @Autowired
//    public WharfController(WharfService wharfService) {
//        this.wharfService = wharfService;
//    }
//
//    // 부두별 직원 조회
//    @GetMapping("/wharf/current-person/{wharfId}")
//    public ResponseEntity<List<Person>> getCurrentEntrantsByWharf(@PathVariable String wharfId) {
//        List<Person> currentPerson = wharfService.getCurrentPersonByWharf(wharfId);
//        return ResponseEntity.ok(currentPerson);
//    }
//
//
//    // 부두별 손님 조회
//    @GetMapping("/wharf/current-guest/{wharfId}")
//    public ResponseEntity<List<Guest>> getCurrentGuestByWharf(@PathVariable String wharfId) {
//        List<Guest> currentGuest = wharfService.getCurrentGuestByWharf(wharfId);
//        return ResponseEntity.ok(currentGuest);
//    }
//
//}
