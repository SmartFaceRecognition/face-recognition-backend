package com.Han2m.portLogistics.user.service;


import com.Han2m.portLogistics.exception.EntityNotFoundException;
import com.Han2m.portLogistics.user.domain.PersonWharf;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.domain.Worker;
import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
import com.Han2m.portLogistics.user.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WorkerService {

    private final S3Service s3Service;
    private final PersonWharfService personWharfService;
    private final WharfService wharfService;
    private final WorkerRepository workerRepository;

    // Worker 조회
    @Transactional(readOnly = true)
    public Worker find(Long personId) {
        return workerRepository.findById(personId).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteWorker(Long personId) {
        workerRepository.delete(workerRepository.findById(personId).orElseThrow(EntityNotFoundException::new));
    }

    public Worker registerWorker(MultipartFile faceImg, ReqWorkerDto reqWorkerDto) throws IOException {

        List<Wharf> wharfList = reqWorkerDto.getWharfs().stream().map(wharfService::find).toList();

        //Dto to entity
        Worker worker = reqWorkerDto.toEntity();

        personWharfService.matchPersonWharf(worker,wharfList);

        //worker 정보 DB에 저장하여 personId 갱신
        workerRepository.save(worker);

        //저장한 worker의 Id 값을 가져와서 s3에 얼굴 이미지 저장
        String faceImgUrl = s3Service.uploadFaceImg(faceImg,worker.getPersonId());

        //worker 정보 수정(faceImgUrl 삽입)
        worker.setFaceUrl(faceImgUrl);

        return worker;
    }


    public Worker editWorker(Long personId, MultipartFile faceImg, ReqWorkerDto reqWorkerDto) throws IOException {

        Worker worker = find(personId);

        personWharfService.deleteByPerson(worker);

        worker.updateWorker(reqWorkerDto);

        List<Wharf> wharfList = reqWorkerDto.getWharfs().stream().map(wharfService::find).toList();

        List<PersonWharf> personWharfList = worker.getPersonWharfList();

        personWharfService.matchPersonWharf(worker,wharfList);

        worker.setPersonWharfList(personWharfList);

        //Worker의 이미지 수정
        if(faceImg != null){
            String faceImgUrl = s3Service.uploadFaceImg(faceImg,personId);
            worker.setFaceUrl(faceImgUrl);
        }

       return worker;
    }

    // Worker 삭제



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

}