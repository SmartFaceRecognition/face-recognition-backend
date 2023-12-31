package com.Han2m.portLogistics.user.controller;

import com.Han2m.portLogistics.exception.ApiResponse;
import com.Han2m.portLogistics.user.domain.Worker;
import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
import com.Han2m.portLogistics.user.dto.res.ResWorkerDto;
import com.Han2m.portLogistics.user.dto.res.ResWorkerSimpleDto;
import com.Han2m.portLogistics.user.service.S3Service;
import com.Han2m.portLogistics.user.service.WorkerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.Han2m.portLogistics.exception.ApiResponse.successResponse;
import static com.Han2m.portLogistics.exception.ApiResponse.successResponseNoContent;

@Tag(name = "직원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WorkerController {

    private final WorkerService workerService;
    private final S3Service s3Service;

    @Operation(summary = "직원 정보 조회")
    @GetMapping("/worker/{id}")
    public ApiResponse<ResWorkerDto> getWorker(@PathVariable @Schema(description = "Worker Id", example = "1") Long id) {

        Worker worker = workerService.find(id);

        return successResponse(new ResWorkerDto(worker));
    }

    @Operation(summary = "모든 직원 정보 조회")
    @GetMapping("/workers")
    public ApiResponse<List<ResWorkerSimpleDto>> getWorkerList(@RequestParam(name = "search",defaultValue = "") String name,
                                                                @RequestParam(name = "sort", defaultValue = "0") int sort,
                                                               @RequestParam(name = "dir", defaultValue = "1") int dir) {

        List<ResWorkerSimpleDto> resWorkerSimpleDtoList = workerService.findAllWorkerAndWharf(name,sort,dir).stream().map(ResWorkerSimpleDto::new).toList();

        return successResponse(resWorkerSimpleDtoList);
    }

    @Operation(summary = "직원 정보 등록하기")
    @PostMapping(value = "/worker")
    public ApiResponse<ResWorkerDto> registerWorker(@RequestBody ReqWorkerDto reqWorkerDto){

        Worker worker = workerService.registerWorker(reqWorkerDto);

        return successResponse(new ResWorkerDto(worker));
    }

    @Operation(summary = "직원 이미지 등록 및 수정")
    @PutMapping(value= "/worker/{id}/img",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> registerWorkerImg(@PathVariable Long id,@Parameter(description = "직원 얼굴 이미지 파일") @RequestParam MultipartFile faceImg) throws IOException {

        s3Service.uploadFaceImg(id,faceImg);

        return successResponseNoContent();
    }

    @Operation(summary = "직원 정보 수정하기")
    @PutMapping(value = "/worker/{id}")
    public ApiResponse<ResWorkerDto> updateWorker(@PathVariable @Schema(description = "Worker Id", example = "1") Long id,@RequestBody ReqWorkerDto reqWorkerDto){
        Worker worker = workerService.editWorker(id,reqWorkerDto);

        return successResponse(new ResWorkerDto(worker));
    }


    @Operation(summary = "직원 정보 삭제하기")
    @DeleteMapping("/worker/{id}")
    public ApiResponse<?> deleteWorkerById(@PathVariable @Schema(description = "Worker Id", example = "1") Long id) {

        workerService.deleteWorker(id);

        return successResponseNoContent();
    }

}
