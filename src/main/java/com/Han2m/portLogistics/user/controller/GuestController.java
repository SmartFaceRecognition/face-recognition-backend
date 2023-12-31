package com.Han2m.portLogistics.user.controller;

import com.Han2m.portLogistics.exception.ApiResponse;
import com.Han2m.portLogistics.user.domain.Guest;
import com.Han2m.portLogistics.user.dto.req.ReqGuestDto;
import com.Han2m.portLogistics.user.dto.res.ResGuestDto;
import com.Han2m.portLogistics.user.dto.res.ResGuestSimpleDto;
import com.Han2m.portLogistics.user.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.Han2m.portLogistics.exception.ApiResponse.successResponse;
import static com.Han2m.portLogistics.exception.ApiResponse.successResponseNoContent;

@Tag(name = "게스트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GuestController {

    private final GuestService guestService;

    @Operation(summary = "게스트 정보 조회")
    @GetMapping("/guest/{id}")
    public ApiResponse<ResGuestDto> getGuest(@PathVariable @Schema(description = "Guest Id", example = "1") Long id) {

        Guest guest = guestService.find(id);

        return successResponse(new ResGuestDto(guest));
    }

    @Operation(summary = "모든 게스트 정보 조회")
    @GetMapping("/guests")
    public ApiResponse<List<ResGuestSimpleDto>> getWorkerList() {

        List<Guest> guestList = guestService.findAllGuestAndWharf();

        List<ResGuestSimpleDto> resGuestSimpleDtoList = guestList.stream().map(ResGuestSimpleDto::new).toList();

        return successResponse(resGuestSimpleDtoList);
    }

    @Operation(summary = "게스트 정보 등록하기")
    @PostMapping("/guest")
    public ApiResponse<ResGuestDto> registerGuest(@RequestBody ReqGuestDto reqGuestDto) {

        Guest guest = guestService.registerGuest(reqGuestDto);

        return successResponse(new ResGuestDto(guest));
    }

    @Operation(summary = "게스트 정보 수정하기")
    @PutMapping("/guest/{id}")
    public ApiResponse<ResGuestDto> updateGuest(@PathVariable Long id, @RequestBody ReqGuestDto reqGuestDto) {

        Guest guest = guestService.editGuestInfo(id, reqGuestDto);

        return successResponse(new ResGuestDto(guest));
    }

    @Operation(summary = "게스트 정보 삭제하기")
    @DeleteMapping("/guest/{id}")
    public ApiResponse<?> deleteGuestById(@PathVariable Long id) {

        guestService.deleteGuest(id);

        return successResponseNoContent();
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
