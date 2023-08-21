package com.Han2m.portLogistics.user.controller;

import com.Han2m.portLogistics.user.dto.req.ReqGuestDto;
import com.Han2m.portLogistics.user.dto.res.ResGuestDto;
import com.Han2m.portLogistics.user.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.Han2m.portLogistics.response.ResBody.successResponse;

@RestController
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @GetMapping("/guest/{id}")
    public ResponseEntity<Object> getGuestById(@PathVariable Long id) {

        ResGuestDto resGuestDto = guestService.getGuestById(id);
        return successResponse(resGuestDto);
    }

    @PostMapping("/guest/register")
    public ResponseEntity<Object> registerGuest(@RequestBody @Validated ReqGuestDto reqGuestDto) {
        ResGuestDto registeredGuest = guestService.registerGuest(reqGuestDto);
        return successResponse(registeredGuest);
    }

    @PutMapping("/guest/{id}")
    public ResponseEntity<Object> updateGuest(@PathVariable Long id, @RequestBody @Validated ReqGuestDto reqGuestDto) {

        ResGuestDto updatedReqGuestDto = guestService.editGuestInfo(id, reqGuestDto);
        return successResponse(updatedReqGuestDto);
    }

    @DeleteMapping("/guest/{id}")
    public ResponseEntity<Object> deleteGuestById(@PathVariable Long id) {
        guestService.deleteGuest(id);
        return successResponse();
    }

    @GetMapping("/guests")
    public ResponseEntity<Object> searchAllGuest(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ResGuestDto> workers = guestService.getAllGuests(pageable);
        return successResponse(workers);
    }

    //직원 이름으로 조회
    @GetMapping("/guest/search")
    public ResponseEntity<Object> searchGuestByName(@RequestParam String name) {
        List<ResGuestDto> workers = guestService.searchGuestByName(name);
        return successResponse(workers);
    }
}
