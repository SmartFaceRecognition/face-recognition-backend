package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
import com.Han2m.portLogistics.user.entity.*;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WharfService {

    private final WharfRepository wharfRepository;

    @Autowired
    public WharfService(WharfRepository wharfRepository) {
        this.wharfRepository = wharfRepository;
    }

    // 부두별 총 인원 수 조회
//    public int getTotalPersonByWharfName(String wharfName) {
//        List<Wharf> wharfs = wharfRepository.findByPlace(wharfName);
//        if (wharfs.isEmpty()) {
//            throw new EntityNotFoundException("The wharf with name " + wharfName + " does not exist.");
//        }
//        Wharf wharf = wharfs.get(0);
//        List<Person> persons = wharf.getWorkerWharfList().stream()
//                .map(WorkerWharf::getPerson)
//                .collect(Collectors.toList());
//        return persons.size();
//    }


    // 부두별 현재 직원만 조회
    public List<ReqWorkerDto> getCurrentWorkerByWharf(String wharf) {
        return wharfRepository.findWorkersByPlace(wharf).stream()
                .map(person -> (Worker) person)
                .map(this::convertToWorkerDTO)
                .collect(Collectors.toList());
    }

    private ReqWorkerDto convertToWorkerDTO(Worker worker) {
        ReqWorkerDto reqWorkerDto = new ReqWorkerDto();
        reqWorkerDto.setNationality(worker.getNationality());
        reqWorkerDto.setName(worker.getName());
        reqWorkerDto.setBirth(worker.getBirth());
        reqWorkerDto.setSex(worker.getSex());
        reqWorkerDto.setPhone(worker.getPhone());
        reqWorkerDto.setPosition(worker.getPosition());


        // 부두 관련 정보 추가
        List<String> wharfs = worker.getWorkerWharfList().stream()
                .map(WorkerWharf::getWharf)
                .map(Wharf::getPlace)
                .collect(Collectors.toList());
        reqWorkerDto.setWharfs(wharfs);

        return reqWorkerDto;
    }


}
