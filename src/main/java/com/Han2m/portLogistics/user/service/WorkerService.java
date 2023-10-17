package com.Han2m.portLogistics.user.service;


import com.Han2m.portLogistics.exception.EntityNotFoundException;
import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
import com.Han2m.portLogistics.user.dto.res.ResWorkerDto;
import com.Han2m.portLogistics.user.entity.Wharf;
import com.Han2m.portLogistics.user.entity.Worker;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import com.Han2m.portLogistics.user.repository.WorkerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WorkerService {

    private final S3Service s3Service;
    private final WorkerRepository workerRepository;
    private final WharfRepository wharfRepository;

    // Worker 조회
    @Transactional(readOnly = true)
    public ResWorkerDto getWorkerById(Long personId) {

        Worker worker = workerRepository.findById(personId).orElseThrow(EntityNotFoundException::new);

        return worker.toResWorkerDto();
    }

    // Worker 등록
    public ResWorkerDto registerWorker(MultipartFile faceImg, ReqWorkerDto reqWorkerDto) throws IOException {

        //Dto to entity
        Worker worker = reqWorkerDto.toEntity();
        worker.setWharfList(reqWorkerDto.getWharfs().stream().map(wharfRepository::findByName).collect(Collectors.toList()));

        //worker 정보 DB에 저장
        Worker savedWorker =  workerRepository.save(worker);

        //저장한 worker의 Id 값을 가져와서 s3에 얼굴 이미지 저장
        String faceImgUrl = s3Service.uploadFaceImg(faceImg,savedWorker.getPersonId());

        //worker 정보 수정(faceImgUrl 삽입)
        savedWorker.setFaceUrl(faceImgUrl);
        workerRepository.save(savedWorker);

        return savedWorker.toResWorkerDto();
    }

    //Worker 수정
    public ResWorkerDto editWorker(Long personId, MultipartFile faceImg, ReqWorkerDto reqWorkerDto) throws IOException {

        Worker worker = workerRepository.findById(personId).orElseThrow(EntityNotFoundException::new);

        //Worker의 정보 수정
        if(reqWorkerDto.getWharfs() != null) {
            worker.setWharfList(reqWorkerDto.getWharfs().stream().map(wharfRepository::findByName).collect(Collectors.toList()));
        }
        worker.updateWorker(reqWorkerDto);

        //Worker의 이미지 수정
        if(!faceImg.isEmpty()){
            String faceImgUrl = s3Service.uploadFaceImg(faceImg,personId);
            worker.setFaceUrl(faceImgUrl);
        }

       return worker.toResWorkerDto();
    }

    // Worker 삭제
    public void deleteWorker(Long personId) {
        workerRepository.delete(workerRepository.findById(personId).orElseThrow(EntityNotFoundException::new));
    }


//    @Transactional(readOnly = true)
//    public Page<ResWorkerDto> getAllWorkers(Pageable pageable) {
//        Page<Worker> workers = workerRepository.findAll(pageable);
//        return workers.map(ResWorkerDto::new);
//    }
//
//    @Transactional(readOnly = true)
//    public List<ResWorkerDto> searchWorkerByName(String name) {
//        List<Worker> workers = workerRepository.findByName(name);
//        return workers.stream().map(ResWorkerDto::new).collect(Collectors.toList());
//    }


    // 테스트용 랜덤부두, 인스턴스 생성시 아래 메소드 자동 호출
    @PostConstruct
    public void createTestWharfs() {
        Wharf wharf1 = new Wharf(1L, "제 1 부두");
        Wharf wharf2 = new Wharf(2L, "제 2 부두");
        Wharf wharf3 = new Wharf(3L, "제 3 부두");
        wharfRepository.saveAll(List.of(wharf1, wharf2, wharf3));
    }


}