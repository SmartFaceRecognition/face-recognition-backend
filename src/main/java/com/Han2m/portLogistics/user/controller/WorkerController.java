package com.Han2m.portLogistics.user.controller;

import com.Han2m.portLogistics.user.dto.req.CreateGroup;
import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
import com.Han2m.portLogistics.user.dto.res.ResWorkerDto;
import com.Han2m.portLogistics.user.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    // Worker 조회
    @GetMapping("/worker/{id}")
    public ResponseEntity<ResWorkerDto> getWorker(@PathVariable Long id) {

        ResWorkerDto resWorkerDto = workerService.getWorkerById(id);

        return ResponseEntity.ok(resWorkerDto);
    }

    //TODO IOException 예외처리 확실하게
    //Worker 등록
    @PostMapping("/worker/register")
    public ResponseEntity<ResWorkerDto> registerWorker(@RequestParam MultipartFile faceImg,
                                                 @RequestPart @Validated(CreateGroup.class) ReqWorkerDto reqWorkerDto) throws IOException {

        ResWorkerDto resWorkerDto = workerService.registerWorker(faceImg,reqWorkerDto);

        return ResponseEntity.ok(resWorkerDto);
    }

    //Worker 수정
    @PutMapping("/worker/edit/{id}")
    public ResponseEntity<ResWorkerDto> updateWorker(@PathVariable Long id, @RequestParam MultipartFile faceImg,
                                               @RequestPart @Validated ReqWorkerDto reqWorkerDto) throws IOException {

        ResWorkerDto resWorkerDto = workerService.editWorker(id,faceImg ,reqWorkerDto);

        return ResponseEntity.ok(resWorkerDto);
    }


    //Worker 삭제
    @DeleteMapping("/worker/{id}")
    public ResponseEntity<? extends HttpEntity> deleteWorkerById(@PathVariable Long id) {
        workerService.deleteWorker(id);
        return ResponseEntity.noContent().build();
    }

//
//    //직원들 조회
//    @GetMapping("/workers")
//    public ResponseEntity<Page<ResWorkerDto>> searchAllWorkers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<ResWorkerDto> workers = workerService.getAllWorkers(pageable);
//        return ResponseEntity.ok(workers);
//    }
//
//    //직원 이름으로 조회
//    @GetMapping("/worker/search")
//    public ResponseEntity<List<ResWorkerDto>> searchPersonsByName(@RequestParam String name) {
//        List<ResWorkerDto> workers = workerService.searchWorkerByName(name);
//        return ResponseEntity.ok(workers);
//    }

}
