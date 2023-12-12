package com.Han2m.portLogistics.user.service;


import com.Han2m.portLogistics.exception.EntityNotFoundException;
import com.Han2m.portLogistics.user.domain.PersonWharf;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.domain.Worker;
import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
import com.Han2m.portLogistics.user.dto.res.ResWorkerDto;
import com.Han2m.portLogistics.user.repository.PersonWharfRepository;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import com.Han2m.portLogistics.user.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WorkerService {

    private final S3Service s3Service;
    private final WorkerRepository workerRepository;
    private final WharfRepository wharfRepository;
    private final PersonWharfRepository personWharfRepository;

    // Worker 조회
    @Transactional(readOnly = true)
    public ResWorkerDto getWorkerById(Long personId) {

        Worker worker = workerRepository.findById(personId).orElseThrow(EntityNotFoundException::new);

        return worker.toResWorkerDto();
    }


    public ResWorkerDto registerWorker(MultipartFile faceImg, ReqWorkerDto reqWorkerDto) throws IOException {

        List<Wharf> wharfList = reqWorkerDto.getWharfs().stream().map(wharfRepository::findById).filter(Optional::isPresent).map(Optional::get).toList();

        //Dto to entity
        Worker worker = reqWorkerDto.toEntity();

        for(Wharf wharf:wharfList){
            PersonWharf personWharf = new PersonWharf(worker,wharf);
            worker.getPersonWharfList().add(personWharf);
        }

        //worker 정보 DB에 저장
        Worker savedWorker =  workerRepository.save(worker);

        //저장한 worker의 Id 값을 가져와서 s3에 얼굴 이미지 저장
        String faceImgUrl = s3Service.uploadFaceImg(faceImg,savedWorker.getPersonId());

        //worker 정보 수정(faceImgUrl 삽입)
        savedWorker.setFaceUrl(faceImgUrl);

        return savedWorker.toResWorkerDto();
    }


    public ResWorkerDto editWorker(Long personId, MultipartFile faceImg, ReqWorkerDto reqWorkerDto) throws IOException {

        Worker worker = workerRepository.findById(personId).orElseThrow(EntityNotFoundException::new);
        personWharfRepository.deleteByPerson(worker);

        worker.updateWorker(reqWorkerDto);

        List<Wharf> wharfList = reqWorkerDto.getWharfs().stream().map(wharfRepository::findById).filter(Optional::isPresent).map(Optional::get).toList();

        List<PersonWharf> personWharfList = worker.getPersonWharfList();

        for (Wharf wharf : wharfList) {
            PersonWharf personWharf = new PersonWharf(worker, wharf);
            personWharfList.add(personWharf);
        }

        worker.setPersonWharfList(personWharfList);

        //Worker의 이미지 수정
        if(faceImg != null){
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

}