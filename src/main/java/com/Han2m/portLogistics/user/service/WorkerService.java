package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.exception.EntityNotFoundException;
import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
import com.Han2m.portLogistics.user.dto.res.ResWorkerDto;
import com.Han2m.portLogistics.user.entity.PersonWharf;
import com.Han2m.portLogistics.user.entity.Signup;
import com.Han2m.portLogistics.user.entity.Wharf;
import com.Han2m.portLogistics.user.entity.Worker;
import com.Han2m.portLogistics.user.repository.PersonRepository;
import com.Han2m.portLogistics.user.repository.PersonWharfRepository;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import com.Han2m.portLogistics.user.repository.WorkerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
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
        // 현재 로그인된 사용자의 정보 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentMemberId = auth.getName();

        // 현재 로그인된 사용자의 Worker 엔티티 가져오기
        Optional<Worker> workerOptional = workerRepository.findBySignupMemberId(currentMemberId);
        if (!workerOptional.isPresent()) {
            throw new RuntimeException("현재 로그인된 사용자의 정보를 찾을 수 없습니다.");
        }

        Worker currentWorker = workerOptional.get();

        currentWorker.setNationality(reqWorkerDto.getNationality());
        currentWorker.setName(reqWorkerDto.getName());
        currentWorker.setSex(reqWorkerDto.getSex());
        currentWorker.setCompany(reqWorkerDto.getCompany());
        currentWorker.setBirth(reqWorkerDto.getBirth());
        currentWorker.setPhone(reqWorkerDto.getPhone());
        currentWorker.setPosition(reqWorkerDto.getPosition());

        List<PersonWharf> workerWharves = new ArrayList<>();
        for (String place : reqWorkerDto.getWharfs()) {
            Wharf wharf = wharfRepository.findByPlace(place).get(0);
            workerWharves.add(new PersonWharf(currentWorker, wharf));
        }
        currentWorker.setPersonWharfList(workerWharves);

        personRepository.save(currentWorker);

        return currentWorker;
    }


    // Worker 수정
    public Worker editWorkerInfo(Long id, ReqWorkerDto reqWorkerDto) {

        Worker worker = workerRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        worker.setNationality(reqWorkerDto.getNationality());
        worker.setName(reqWorkerDto.getName());
        worker.setSex(reqWorkerDto.getSex());
        worker.setCompany(reqWorkerDto.getCompany());
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
        workerRepository.save(worker);
        return new ResWorkerDto(worker);
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