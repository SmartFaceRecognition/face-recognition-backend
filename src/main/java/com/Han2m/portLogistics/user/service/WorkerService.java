package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.admin.entitiy.Member;
import com.Han2m.portLogistics.admin.repository.MemberRepository;
import com.Han2m.portLogistics.exception.EntityNotFoundException;
import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
import com.Han2m.portLogistics.user.dto.res.ResWorkerDto;
import com.Han2m.portLogistics.user.entity.PersonWharf;
import com.Han2m.portLogistics.user.entity.Wharf;
import com.Han2m.portLogistics.user.entity.Worker;
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

    private final WorkerRepository workerRepository;
    private final WharfRepository wharfRepository;
    private final PersonWharfRepository personWharfRepository;
    private final MemberRepository memberRepository;


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
        workerRepository.save(worker);
        return worker;
    }



    // Worker 수정
    public Worker editWorkerInfo(Long workerId, ReqWorkerDto reqWorkerDto) {
        Optional<Worker> workerOptional = workerRepository.findById(workerId);
        if (!workerOptional.isPresent()) {
            throw new EntityNotFoundException();
        }

        Worker worker = workerOptional.get();

        // Worker의 정보 수정
        worker.setNationality(reqWorkerDto.getNationality());
        worker.setName(reqWorkerDto.getName());
        worker.setSex(reqWorkerDto.getSex());
        worker.setCompany(reqWorkerDto.getCompany());
        worker.setBirth(reqWorkerDto.getBirth());
        worker.setPhone(reqWorkerDto.getPhone());
        worker.setPosition(reqWorkerDto.getPosition());

        // 기존의 PersonWharf 정보 삭제
        for (PersonWharf personWharf : worker.getPersonWharfList()) {
            personWharfRepository.deleteById(personWharf.getPersonWharfId());
        }
        worker.setPersonWharfList(new ArrayList<>());

        // 새로운 Wharf 정보 바탕으로 PersonWharf 리스트 생성
        List<PersonWharf> workerWharves = new ArrayList<>();
        for (String place : reqWorkerDto.getWharfs()) {
            Wharf wharf = wharfRepository.findByPlace(place).get(0);
            workerWharves.add(new PersonWharf(worker, wharf));
        }
        worker.setPersonWharfList(workerWharves);

        // 변경된 Worker 저장
        workerRepository.save(worker);

        return worker;
    }

    // 인원 삭제
    public void deleteWorker(Long id) {
        if (!workerRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        workerRepository.deleteById(id);
    }


    //url 저장
    public ResWorkerDto registerWorkerUrl(Worker worker, String faceUrl) {

        worker.setFaceUrl(faceUrl);
        workerRepository.save(worker);
        return new ResWorkerDto(worker);
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