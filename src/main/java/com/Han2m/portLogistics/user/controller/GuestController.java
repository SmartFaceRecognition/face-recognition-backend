package com.Han2m.portLogistics.user.controller;

import com.Han2m.portLogistics.user.dto.GuestDto;
import com.Han2m.portLogistics.user.dto.PersonDto;
import com.Han2m.portLogistics.user.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @GetMapping("/guest/{id}")
    public ResponseEntity<GuestDto> getGuestById(@PathVariable Long id) {
        GuestDto guestDto = guestService.getGuestById(id);
        return ResponseEntity.ok(guestDto);
    }

    @PostMapping("/guest/register")
    public ResponseEntity<GuestDto> registerGuest(@RequestBody GuestDto guestDto) {
        GuestDto savedGuestDto = guestService.registerGuest(guestDto);
        return ResponseEntity.ok(savedGuestDto);
    }

    @PutMapping("/guest/{id}")
    public ResponseEntity<GuestDto> editGuestInfo(@PathVariable Long id, @RequestBody GuestDto updatedGuestDto) {
        GuestDto updatedGuest = guestService.editGuestInfo(id, updatedGuestDto);
        return ResponseEntity.ok(updatedGuest);
    }

    @DeleteMapping("/guest/{id}")
    public ResponseEntity<String> deleteGuest(@PathVariable Long id) {
        guestService.deleteGuest(id);
        String responseMessage = id + "이(가) 삭제되었습니다.";
        return ResponseEntity.ok(responseMessage);
    }

    // 이름으로 검색
    @GetMapping("/guest/search")
    public ResponseEntity<List<GuestDto>> searchGuestByName(@RequestParam String name) {
        List<GuestDto> guests = guestService.searchGuestByName(name);
        return ResponseEntity.ok(guests);
    }



// 페이징 등 기능 추가


}
