package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.exception.EntityNotFoundException;
import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
import com.Han2m.portLogistics.user.dto.res.ResWorkerDto;
import com.Han2m.portLogistics.user.entity.PersonWharf;
import com.Han2m.portLogistics.user.entity.Wharf;
import com.Han2m.portLogistics.user.entity.Worker;
import com.Han2m.portLogistics.user.repository.PersonRepository;
import com.Han2m.portLogistics.user.repository.PersonWharfRepository;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import com.Han2m.portLogistics.user.repository.WorkerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class WorkerService {

    private final PersonRepository personRepository;
    private final WorkerRepository workerRepository;
    private final WharfRepository wharfRepository;
    private final PersonWharfRepository personWharfRepository;



    // Worker 조회
    public ResWorkerDto getWorkerById(Long id) {

        Worker worker = workerRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return new ResWorkerDto(worker);
    }

    // Worker 등록
    public Worker registerWorker(ReqWorkerDto reqWorkerDto) {

        Worker worker = new Worker();

        worker.setNationality(reqWorkerDto.getNationality());
        worker.setName(reqWorkerDto.getName());
        worker.setSex(reqWorkerDto.getSex());
        worker.setBirth(reqWorkerDto.getBirth());
        worker.setPhone(reqWorkerDto.getPhone());
        worker.setPosition(reqWorkerDto.getPosition());

        List<PersonWharf> workerWharves = worker.getPersonWharfList();

        for (String place : reqWorkerDto.getWharfs()) {
            Wharf wharf = wharfRepository.findByPlace(place).get(0);
            workerWharves.add(new PersonWharf(worker, wharf));
        }

        worker.setPersonWharfList(workerWharves);
        personRepository.save(worker);
        return worker;
    }

    public Worker editWorkerInfo(Long id, ReqWorkerDto reqWorkerDto) {

        Worker worker = workerRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        worker.setNationality(reqWorkerDto.getNationality());
        worker.setName(reqWorkerDto.getName());
        worker.setSex(reqWorkerDto.getSex());
        worker.setBirth(reqWorkerDto.getBirth());
        worker.setPhone(reqWorkerDto.getPhone());
        worker.setPosition(reqWorkerDto.getPosition());

        //기존 정보 삭제
        for (PersonWharf PersonWharf : worker.getPersonWharfList()) {
            personWharfRepository.deleteById(PersonWharf.getPersonWharfId());
        }
        worker.setPersonWharfList(new ArrayList<>());

        List<PersonWharf> workerWharves = worker.getPersonWharfList();

        for (String place : reqWorkerDto.getWharfs()) {
            Wharf wharf = wharfRepository.findByPlace(place).get(0);
            workerWharves.add(new PersonWharf(worker, wharf));
        }

        worker.setPersonWharfList(workerWharves);


        workerRepository.save(worker);

        return worker;
    }

    //url 저장
    public ResWorkerDto registerWorkerUrl(Worker worker, String faceUrl) {

        worker.setFaceUrl(faceUrl);
        sendDataToApi(worker.getPersonId().toString(),faceUrl);

        return new ResWorkerDto(worker);
    }

    public void sendDataToApi(String id,String faceurl){

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String apiUrl = "http://127.0.0.1:5000/face"; // 대상 API의 URL
        String jsonBody = "{\"id\": \"" + id + "\", \"url\": \"" + faceurl + "\"}"; // 올바른 JSON 데이터 형식

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        restTemplate.postForEntity(apiUrl, requestEntity, String.class);
    }

    // 인원 삭제
    public void deleteWorker(Long id) {
        workerRepository.findById(id).map(worker -> {
            workerRepository.delete(worker);
            return worker;
        }).orElseThrow(EntityNotFoundException::new);
    }


    public Page<ResWorkerDto> getAllWorkers(Pageable pageable) {
        Page<Worker> workers = workerRepository.findAll(pageable);
        return workers.map(ResWorkerDto::new);
    }

    public List<ResWorkerDto> searchWorkerByName(String name) {
        List<Worker> workers = workerRepository.findByName(name);
        return workers.stream().map(ResWorkerDto::new).collect(Collectors.toList());
    }


    // 테스트용 랜덤부두, 인스턴스 생성시 아래 메소드 자동 호출
    @PostConstruct
    public void createTestWharfs() {
        Wharf wharf1 = new Wharf(1L, "1wharf");
        Wharf wharf2 = new Wharf(2L, "2wharf");
        Wharf wharf3 = new Wharf(3L, "3wharf");
        Wharf wharf4 = new Wharf(4L, "4wharf");
        Wharf wharf5 = new Wharf(5L, "5wharf");
        wharfRepository.saveAll(List.of(wharf1, wharf2, wharf3, wharf4, wharf5));
    }
}
