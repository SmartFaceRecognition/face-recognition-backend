package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.user.dto.GuestDto;
import com.Han2m.portLogistics.user.dto.PersonDto;
import com.Han2m.portLogistics.user.dto.WorkerDto;
import com.Han2m.portLogistics.user.entity.*;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public int getTotalPersonByWharfName(String wharfName) {
        List<Wharf> wharfs = wharfRepository.findByPlace(wharfName);
        if (wharfs.isEmpty()) {
            throw new EntityNotFoundException("The wharf with name " + wharfName + " does not exist.");
        }
        Wharf wharf = wharfs.get(0);
        List<Person> persons = wharf.getPersonWharfList().stream()
                .map(PersonWharf::getPerson)
                .collect(Collectors.toList());
        return persons.size();
    }

    // 부두별 직원 + 손님 모두 조회
    public List<PersonDto> getCurrentPersonsByWharf(String wharf) {
        List<WorkerDto> workers = getCurrentWorkerByWharf(wharf);
        List<GuestDto> guests = getCurrentGuestByWharf(wharf);

        List<PersonDto> persons = new ArrayList<>();
        persons.addAll(workers);
        persons.addAll(guests);

        return persons;
    }


    // 부두별 현재 직원만 조회
    public List<WorkerDto> getCurrentWorkerByWharf(String wharf) {
        return wharfRepository.findWorkersByPlace(wharf).stream()
                .map(person -> (Worker) person)
                .map(this::convertToWorkerDTO)
                .collect(Collectors.toList());
    }

    // 부두별 현재 손님만 조회
    public List<GuestDto> getCurrentGuestByWharf(String wharf) {
        return wharfRepository.findGuestsByPlace(wharf).stream()
                .map(person -> (Guest) person)
                .map(this::convertToGuestDTO)
                .collect(Collectors.toList());
    }

    private WorkerDto convertToWorkerDTO(Worker worker) {
        WorkerDto workerDto = new WorkerDto();
        workerDto.setId(worker.getId());
        workerDto.setNationality(worker.getNationality());
        workerDto.setName(worker.getName());
        workerDto.setBirth(worker.getBirth());
        workerDto.setSex(worker.getSex());
        workerDto.setPhone(worker.getPhone());
        workerDto.setPosition(worker.getPosition());
        workerDto.setFaceUrl(worker.getFaceUrl());
        workerDto.setFingerPrint(worker.getFingerprint());
        return workerDto;
    }


    private GuestDto convertToGuestDTO(Guest guest) {
        GuestDto guestDto = new GuestDto();
        guestDto.setId(guest.getId());
        guestDto.setNationality(guest.getNationality());
        guestDto.setName(guest.getName());
        guestDto.setBirth(guest.getBirth());
        guestDto.setSex(guest.getSex());
        guestDto.setPhone(guest.getPhone());
        guestDto.setSsn(guest.getSsn());
        return guestDto;
    }

}
