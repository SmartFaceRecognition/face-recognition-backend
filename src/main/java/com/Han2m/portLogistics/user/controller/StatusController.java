package com.Han2m.portLogistics.user.controller;


import com.Han2m.portLogistics.exception.ApiResponse;
import com.Han2m.portLogistics.user.dto.res.ResStatusDto;
import com.Han2m.portLogistics.user.service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.Han2m.portLogistics.exception.ApiResponse.successResponse;
import static com.Han2m.portLogistics.exception.ApiResponse.successResponseNoContent;

@Tag(name = "출입관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/person")
public class StatusController {

    private final StatusService statusService;

    @Operation(summary = "들어온 시간 등록")
    @PostMapping("/{id}/enter/{wharfId}")
    public ApiResponse<?> personEnter(@PathVariable Long id, @PathVariable Long wharfId){
        statusService.registerPersonEnter(id, wharfId);
        return successResponseNoContent();
    }

    @Operation(summary = "퇴장 시간 등록")
    @PostMapping("/{id}/out/{wharfId}")
    public ApiResponse<?> personOut(@PathVariable Long id,@PathVariable Long wharfId){
        statusService.registerPersonOut(id, wharfId);
        return successResponseNoContent();
    }

    @Operation(summary = "부두내 인원 조회")
    @GetMapping("/wharf/{wharfId}")
    public ApiResponse<List<ResStatusDto>> personInWharf(@PathVariable Long wharfId){

        List<ResStatusDto> statues;

        //wharfId가 없으면 부두내에 있는 인원 모두 조회
        if (wharfId == null) {
            statues = statusService.getAllWorkerInWharf();
        }
        //wharfId가 있으면 해당 부두내에 있는 인원만 조회
        else{
            statues = statusService.getWorkerInWharf(wharfId);
        }

        return successResponse(statues);
    }

}
