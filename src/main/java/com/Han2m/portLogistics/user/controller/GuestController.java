package com.Han2m.portLogistics.user.controller;

import com.Han2m.portLogistics.user.dto.req.ReqGuestDto;
import com.Han2m.portLogistics.user.dto.res.ResGuestDto;
import com.Han2m.portLogistics.user.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게스트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GuestController {

    private final GuestService guestService;

    @Operation(summary = "게스트 정보 조회")
    @GetMapping("/guest/{id}")
    public ResponseEntity<ResGuestDto> getGuest(@PathVariable @Schema(description = "Guest Id", example = "1") Long id) {

        ResGuestDto resGuestDto = guestService.getGuestById(id);

        return ResponseEntity.ok(resGuestDto);
    }
    @Operation(summary = "게스트 정보 등록하기")
    @PostMapping("/guest")
    public ResponseEntity<ResGuestDto> registerGuest(@RequestBody ReqGuestDto reqGuestDto) {

        ResGuestDto registeredGuest = guestService.registerGuest(reqGuestDto);

        return ResponseEntity.ok(registeredGuest);
    }

    @Operation(summary = "게스트 정보 수정하기")
    @PutMapping("/guest/{id}")
    public ResponseEntity<ResGuestDto> updateGuest(@PathVariable Long id, @RequestBody ReqGuestDto reqGuestDto) {

        ResGuestDto updatedReqGuestDto = guestService.editGuestInfo(id, reqGuestDto);

        return ResponseEntity.ok(updatedReqGuestDto);
    }

    @Operation(summary = "게스트 정보 삭제하기")
    @DeleteMapping("/guest/{id}")
    public ResponseEntity<? extends HttpEntity> deleteGuestById(@PathVariable Long id) {

        guestService.deleteGuest(id);

        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/guests")
//    public ResponseEntity<Page<ResGuestDto>> searchAllGuest(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
//        Pageable pageable = PageRequest.of(page, size);
//      //  Page<ResGuestDto> workers = guestService.getAllGuests(pageable);
//        return ResponseEntity.ok(workers);
//    }
//
//    //직원 이름으로 조회
//    @GetMapping("/guest/search")
//    public ResponseEntity<List<ResGuestDto>> searchGuestByName(@RequestParam String name) {
//     //   List<ResGuestDto> workers = guestService.searchGuestByName(name);
//        return ResponseEntity.ok(workers);
//    }
}
