package com.Han2m.portLogistics.user.controller;

import com.Han2m.portLogistics.user.domain.Worker;
import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
import com.Han2m.portLogistics.user.dto.res.ResWorkerDto;
import com.Han2m.portLogistics.user.service.WorkerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "직원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WorkerController {

    private final WorkerService workerService;

    @Operation(summary = "직원 정보 조회")
    @GetMapping("/worker/{id}")
    public ResponseEntity<ResWorkerDto> getWorker(@PathVariable @Schema(description = "Worker Id", example = "1") Long id) {

        Worker worker = workerService.find(id);

        return ResponseEntity.ok(worker.toResWorkerDto());
    }
    @Operation(summary = "직원 정보 등록하기")
    @PostMapping( value = "/worker" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResWorkerDto> registerWorker(@Parameter(description = "직원 얼굴 이미지 파일") @RequestParam MultipartFile faceImg,
                                                       @RequestPart ReqWorkerDto reqWorkerDto) throws IOException {

        Worker worker = workerService.registerWorker(faceImg,reqWorkerDto);

        return ResponseEntity.ok(worker.toResWorkerDto());
    }

    @Operation(summary = "직원 정보 수정하기")
    @PutMapping(value = "/worker/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResWorkerDto> updateWorker(@PathVariable @Schema(description = "Worker Id", example = "1") Long id, @RequestParam(required = false) MultipartFile faceImg,
                                               @RequestPart ReqWorkerDto reqWorkerDto) throws IOException {
        Worker worker = workerService.editWorker(id,faceImg ,reqWorkerDto);

        return ResponseEntity.ok(worker.toResWorkerDto());
    }


    @Operation(summary = "직원 정보 삭제하기")
    @DeleteMapping("/worker/{id}")
    public ResponseEntity<? extends HttpEntity> deleteWorkerById(@PathVariable @Schema(description = "Worker Id", example = "1") Long id) {

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
