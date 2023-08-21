package com.Han2m.portLogistics.user.controller;

import com.Han2m.portLogistics.user.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.Han2m.portLogistics.response.ResBody.successResponse;

@RestController
@RequiredArgsConstructor
public class ArduinoController {

    private final WorkerService workerService;

    //지문데이터 등록 요청
    @PostMapping("/worker/{id}/fingerprint")
    public ResponseEntity<Object> callArduino(@PathVariable Long id){

        //아두이노에게 id 데이터를 주면서 요청
        return successResponse();
    }



}
