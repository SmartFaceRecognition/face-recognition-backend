package com.Han2m.portLogistics.user.controller;

import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
import com.Han2m.portLogistics.user.dto.res.ResWorkerDto;
import com.Han2m.portLogistics.user.entity.Worker;
import com.Han2m.portLogistics.user.service.S3Service;
import com.Han2m.portLogistics.user.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.Han2m.portLogistics.response.ResBody.notFoundResponse;
import static com.Han2m.portLogistics.response.ResBody.successResponse;

@RestController
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;
    private final S3Service s3Service;


    // Worker 조회
    @GetMapping("/worker/{id}")
    public ResponseEntity<Object> getWorkerById(@PathVariable Long id) {

        if(workerService.workerIsPresent(id)){
            return notFoundResponse("해당 직원이 존재하지 않습니다");
        }

        ResWorkerDto resWorkerDto = workerService.getWorkerById(id);
        return successResponse(resWorkerDto);
    }


    //Worker 등록
    @PostMapping("/worker/register")
    public ResponseEntity<Object> registerWorker(@RequestParam MultipartFile faceImg,
                                                      @RequestPart ReqWorkerDto reqWorkerDto)throws IOException {

        Worker worker = workerService.registerWorker(reqWorkerDto);

        //얼굴 이미지 s3에 저장
        String faceUrl = s3Service.uploadFaceImg(faceImg,worker.getPersonId());

        ResWorkerDto resWorkerDto = workerService.registerWorkerUrl(worker,faceUrl);

        return successResponse(resWorkerDto);
    }


    //Worker 수정
    @PutMapping("/worker/{id}")
    public ResponseEntity<Object> updateWorker(@PathVariable Long id,@RequestParam MultipartFile faceImg,
                                                     @RequestPart ReqWorkerDto reqWorkerDto) throws IOException {

        if(workerService.workerIsPresent(id)){
            return notFoundResponse("해당 직원이 존재하지 않습니다");
        }

        Worker worker = workerService.editWorkerInfo(id,reqWorkerDto);

        //얼굴 이미지 s3에 저장
        String faceUrl = s3Service.uploadFaceImg(faceImg,worker.getPersonId());

        ResWorkerDto resWorkerDto = workerService.registerWorkerUrl(worker,faceUrl);

        return successResponse(resWorkerDto);
    }

    //Worker 삭제
    @DeleteMapping("/worker/{id}")
    public ResponseEntity<Object> deleteWorkerById(@PathVariable Long id) {

        if(workerService.workerIsPresent(id)){
            return notFoundResponse("해당 직원이 존재하지 않습니다");
        }

        workerService.deleteWorker(id);

        return successResponse();
    }


    //직원들 조회
    @GetMapping("/workers")
    public ResponseEntity<Object> searchAllWorkers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ResWorkerDto> workers = workerService.getAllWorkers(pageable);
        return successResponse(workers);
    }

    //직원 이름으로 조회
    @GetMapping("/worker/search")
    public ResponseEntity<Object> searchPersonsByName(@RequestParam String name) {
        List<ResWorkerDto> workers = workerService.searchWorkerByName(name);
        return successResponse(workers);
    }

//    // 부두내 모든 직원 조회
//    @GetMapping("/worker/wharf")
//    public ResponseEntity<Object> searchAllWorkersInWharf() {
//      //  Integer numPersons = workerService.getTotalPersonByWharfName(wharfName);
//        //return ResponseEntity.ok();
//    }
//
//    // 부두별 직원 조회
//    @GetMapping("/worker/wharf/{wharfName}")
//    public ResponseEntity<Object> searchAllWorkersInWharfByWharf(@PathVariable String wharfName) {
//       // List<ReqWorkerDto> currentPerson = wharfService.getCurrentWorkerByWharf(wharfName);
//        // return ResponseEntity.ok(currentPerson);
//    }

}
