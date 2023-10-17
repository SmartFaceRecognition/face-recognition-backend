package com.Han2m.portLogistics.user.controller;

import com.Han2m.portLogistics.user.dto.req.ReqGuestDto;
import com.Han2m.portLogistics.user.dto.res.ResGuestDto;
import com.Han2m.portLogistics.user.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    //guest 정보 조희
    @GetMapping("/guest/{id}")
    public ResponseEntity<ResGuestDto> getGuest(@PathVariable Long id) {
        ResGuestDto resGuestDto = guestService.getGuestById(id);
        return ResponseEntity.ok(resGuestDto);
    }

    @PostMapping("/guest/register")
    public ResponseEntity<ResGuestDto> registerGuest(@RequestBody @Validated ReqGuestDto reqGuestDto) {
        ResGuestDto registeredGuest = guestService.registerGuest(reqGuestDto);
        return ResponseEntity.ok(registeredGuest);
    }

    @PutMapping("/guest/{id}")
    public ResponseEntity<ResGuestDto> updateGuest(@PathVariable Long id, @RequestBody @Validated ReqGuestDto reqGuestDto) {

        ResGuestDto updatedReqGuestDto = guestService.editGuestInfo(id, reqGuestDto);
        return ResponseEntity.ok(updatedReqGuestDto);
    }

    @DeleteMapping("/guest/{id}")
    public ResponseEntity<? extends HttpEntity> deleteGuestById(@PathVariable Long id) {
        guestService.deleteGuest(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/guests")
    public ResponseEntity<Page<ResGuestDto>> searchAllGuest(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ResGuestDto> workers = guestService.getAllGuests(pageable);
        return ResponseEntity.ok(workers);
    }

    //직원 이름으로 조회
    @GetMapping("/guest/search")
    public ResponseEntity<List<ResGuestDto>> searchGuestByName(@RequestParam String name) {
        List<ResGuestDto> workers = guestService.searchGuestByName(name);
        return ResponseEntity.ok(workers);
    }
}
