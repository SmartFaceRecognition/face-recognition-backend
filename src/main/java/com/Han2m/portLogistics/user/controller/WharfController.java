//package com.Han2m.portLogistics.user.controller;
//
//import com.Han2m.portLogistics.user.dto.PersonDto;
//import com.Han2m.portLogistics.user.entity.Status;
//import com.Han2m.portLogistics.user.repository.StatusRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//public class WharfController {
//
//    private final StatusRepository statusRepository;
//
//    @Autowired
//    public WharfController(StatusRepository statusRepository) {
//        this.statusRepository = statusRepository;
//    }
//
//    // 특정 부두에 허가 받은 사람들 조회
//    @GetMapping("/wharf/{wharfId}/current-entrants")
//    public ResponseEntity<List<PersonDto>> getCurrentEntrantsByWharf(@PathVariable Long wharfId) {
//        List<Status> currentStatusList = statusRepository.findByWharfAndOutTimeIsNull(wharfId);
//
//        List<PersonDto> entrants = currentStatusList.stream()
//                .map(status -> new PersonDto(
//                        status.getPerson().getId(),
//                        status.getPerson().getNationality(),
//                        status.getPerson().getName(),
//                        status.getPerson().getBirth(),
//                        status.getPerson().getPhone(),
//                        status.getPerson().getPosition(),
//                        status.getEnterTime(),
//                        status.getOutTime()))
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(entrants);
//    }
//}
