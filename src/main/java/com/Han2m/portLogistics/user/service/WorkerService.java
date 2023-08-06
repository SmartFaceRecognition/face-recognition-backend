package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
import com.Han2m.portLogistics.user.dto.res.ResWorkerDto;
import com.Han2m.portLogistics.user.entity.Wharf;
import com.Han2m.portLogistics.user.entity.Worker;
import com.Han2m.portLogistics.user.entity.WorkerWharf;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import com.Han2m.portLogistics.user.repository.WorkerRepository;
import com.Han2m.portLogistics.user.repository.WorkerWharfRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final WharfRepository wharfRepository;
    private final WorkerWharfRepository workerWharfRepository;

    //id 조회
    public boolean workerIsPresent(Long id) {
        return workerRepository.findById(id).isEmpty();
    }


    // Worker 조회
    public ResWorkerDto getWorkerById(Long id) {

        Worker worker = workerRepository.findById(id).get();

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

        List<WorkerWharf> workerWharves = worker.getWorkerWharfList();

        for (String place : reqWorkerDto.getWharfs()) {
            Wharf wharf = wharfRepository.findByPlace(place).get(0);
            workerWharves.add(new WorkerWharf(worker, wharf));
        }

        worker.setWorkerWharfList(workerWharves);
        workerRepository.save(worker);

        workerRepository.save(worker);
        return worker;
    }

    public Worker editWorkerInfo(Long id, ReqWorkerDto reqWorkerDto) {

        Worker worker = workerRepository.findById(id).get();

        worker.setNationality(reqWorkerDto.getNationality());
        worker.setName(reqWorkerDto.getName());
        worker.setSex(reqWorkerDto.getSex());
        worker.setBirth(reqWorkerDto.getBirth());
        worker.setPhone(reqWorkerDto.getPhone());
        worker.setPosition(reqWorkerDto.getPosition());

        //기존 정보 삭제
        for (WorkerWharf workerWharf : worker.getWorkerWharfList()) {
            workerWharfRepository.deleteById(workerWharf.getWorkerWharfId());
        }
        worker.setWorkerWharfList(new ArrayList<>());

        List<WorkerWharf> workerWharves = worker.getWorkerWharfList();

        for (String place : reqWorkerDto.getWharfs()) {
            Wharf wharf = wharfRepository.findByPlace(place).get(0);
            workerWharves.add(new WorkerWharf(worker, wharf));
        }

        worker.setWorkerWharfList(workerWharves);


        workerRepository.save(worker);

        return worker;
    }

    //url 저장
    public ResWorkerDto registerWorkerUrl(Worker worker, String faceUrl, String fingerUrl) {

        worker.setFaceUrl(faceUrl);
        worker.setFingerprintUrl(fingerUrl);

        return new ResWorkerDto(worker);
    }



    // 인원 삭제
    public void deleteWorker(Long id) {
        workerRepository.deleteById(id);
    }

    public Page<ResWorkerDto> getAllWorkers(Pageable pageable) {
        Page<Worker> workers = workerRepository.findAll(pageable);
        return workers.map(ResWorkerDto::new);
    }

    public List<ResWorkerDto> searchPersonByName(String name) {
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
