//package com.Han2m.portLogistics.user.service;
//
//import com.Han2m.portLogistics.user.entity.Guest;
//import com.Han2m.portLogistics.user.entity.Person;
//import com.Han2m.portLogistics.user.entity.Status;
//import com.Han2m.portLogistics.user.repository.WharfRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class WharfService {
//
//    private final WharfRepository wharfRepository;
//
//    @Autowired
//    public WharfService(WharfRepository wharfRepository) {
//        this.wharfRepository = wharfRepository;
//    }
//
//    // 부두별 현재 직원 조회
//    public List<Person> getCurrentPersonByWharf(String wharf) {
//        return wharfRepository.findCurrentPersonByWharf(wharf);
//    }
//
//
//    // 부두별 현재 손님 조회
//    public List<Guest> getCurrentGuestByWharf(String wharf) {
//        return wharfRepository.findCurrentGuestByWharf(wharf);
//    }
//
//}
