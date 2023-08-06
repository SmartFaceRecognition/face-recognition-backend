package com.Han2m.portLogistics.user.controller;

import com.Han2m.portLogistics.user.dto.req.ReqGuestDto;
import com.Han2m.portLogistics.user.dto.res.ResGuestDto;
import com.Han2m.portLogistics.user.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.Han2m.portLogistics.response.ResBody.notFoundResponse;
import static com.Han2m.portLogistics.response.ResBody.successResponse;

@RestController
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @GetMapping("/guest/{id}")
    public ResponseEntity<Object> getGuestById(@PathVariable Long id) {

        if(guestService.guestIsPresent(id)){
            return notFoundResponse("해당 게스트가 존재하지 않습니다");
        }

        ResGuestDto resGuestDto = guestService.getGuestById(id);
        return successResponse(resGuestDto);
    }

    @PostMapping("/guest/register")
    public ResponseEntity<Object> registerGuest(@RequestBody ReqGuestDto reqGuestDto) {
        ResGuestDto registeredGuest = guestService.registerGuest(reqGuestDto);
        return successResponse(registeredGuest);
    }

    @PutMapping("/guest/{id}")
    public ResponseEntity<Object> updateGuest(@PathVariable Long id, @RequestBody ReqGuestDto reqGuestDto) {

        if(guestService.guestIsPresent(id)){
            return notFoundResponse("해당 게스트가 존재하지 않습니다");
        }

        ResGuestDto updatedReqGuestDto = guestService.editGuestInfo(id, reqGuestDto);
        return successResponse(updatedReqGuestDto);
    }

    @DeleteMapping("/guest/{id}")
    public ResponseEntity<Object> deleteGuestById(@PathVariable Long id) {

        if(guestService.guestIsPresent(id)){
            return notFoundResponse("해당 게스트가 존재하지 않습니다");
        }

        guestService.deleteGuest(id);

        return successResponse();
    }
}
