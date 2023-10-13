package com.Han2m.portLogistics.user.controller;


import com.Han2m.portLogistics.user.dto.res.ResStatusDto;
import com.Han2m.portLogistics.user.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    //들어온 시간 저장
    @PostMapping("/worker/{id}/enter/{wharfId}")
    public ResponseEntity<ResStatusDto> workerEnter(@PathVariable Long id,@PathVariable Long wharfId){
        ResStatusDto resStatusDto = statusService.registerWorkerEnter(id, wharfId);
        return ResponseEntity.ok(resStatusDto);
    }

    //퇴장시간 저장
    @PostMapping("/worker/{id}/out/{wharfId}")
    public ResponseEntity<ResStatusDto> workerOut(@PathVariable Long id,@PathVariable Long wharfId){
        ResStatusDto resStatusDto = statusService.registerWorkerOut(id, wharfId);
        return ResponseEntity.ok(resStatusDto);
    }

    //부두내 인원 조회
    @GetMapping("/worker/wharf/{wharfId}")
    public ResponseEntity<List<ResStatusDto>> workerInWharf(@PathVariable Long wharfId){

        List<ResStatusDto> statues;

        //wharfId가 없으면 부두내에 있는 인원 모두 조회
        if (wharfId == null) {
            statues = statusService.getAllWorkerInWharf();
        }
        //wharfId가 있으면 해당 부두내에 있는 인원만 조회
        else{
            statues = statusService.getWorkerInWharf(wharfId);
        }

        return ResponseEntity.ok(statues);
    }

}
