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
@RequestMapping("/api/worker")
public class StatusController {

    private final StatusService statusService;

    @Operation(summary = "들어온 시간 등록")
    @PostMapping("/{id}/enter/{wharfId}")
    public ApiResponse<?> workerEnter(@PathVariable Long id, @PathVariable Long wharfId){
        statusService.registerWorkerEnter(id, wharfId);
        return successResponseNoContent();
    }

    @Operation(summary = "퇴장 시간 등록")
    @PostMapping("/{id}/out/{wharfId}")
    public ApiResponse<?> workerOut(@PathVariable Long id,@PathVariable Long wharfId){
        statusService.registerWorkerOut(id, wharfId);
        return successResponseNoContent();
    }

    @Operation(summary = "부두내 직원 조회")
    @GetMapping("/wharf/{wharfId}")
    public ApiResponse<List<ResStatusDto>> personInWharf(@PathVariable Long wharfId){
        List<ResStatusDto> resStatusDtoList = statusService.findWorkerInWharf(wharfId).stream().map(ResStatusDto::new).toList();
        return successResponse(resStatusDtoList);
    }

}
