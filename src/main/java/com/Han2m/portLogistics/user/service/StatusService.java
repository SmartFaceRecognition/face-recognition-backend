package com.Han2m.portLogistics.user.service;


import com.Han2m.portLogistics.exception.EntityNotFoundException;
import com.Han2m.portLogistics.user.dto.res.ResStatusDto;
import com.Han2m.portLogistics.user.domain.Person;
import com.Han2m.portLogistics.user.domain.Status;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.repository.PersonRepository;
import com.Han2m.portLogistics.user.repository.StatusRepository;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StatusService {

    private final StatusRepository statusRepository;
    private final PersonRepository personRepository;
    private final WharfRepository wharfRepository;

    public void registerPersonEnter(Long id, Long wharfId){
        Person person = personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Wharf wharf = wharfRepository.findById(wharfId).orElseThrow(EntityNotFoundException::new);

        // 입장 시간 (현재 시간)
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        //현재 status 존재하는지 확인
        Optional<Status> status = statusRepository.findByPersonAndWharf(person, wharf);

        if(status.isEmpty()){
            Status newStatus = Status.builder().
                    person(person).
                    wharf(wharf).
                    enterTime(currentTime).
                    build();
            statusRepository.save(newStatus);
        }
        else{
            status.get().updateEnterTImeTime(currentTime);
        }
    }

    public void registerPersonOut(Long id, Long wharfId){
        Person person = personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Wharf wharf = wharfRepository.findById(wharfId).orElseThrow(EntityNotFoundException::new);

        Optional<Status> status = statusRepository.findByPersonAndWharf(person, wharf);
        if(status.isPresent()){
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            status.get().updateOutTime(currentTime);
        }
    }


    @Transactional(readOnly = true)
    public List<ResStatusDto> getAllWorkerInWharf(){
        List<Status> statuses = statusRepository.findByOutTimeIsNull();
        return statuses.stream().map(ResStatusDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResStatusDto> getWorkerInWharf(Long wharfId){
        List<Status> statuses = statusRepository.findByOutTimeIsNullAndWharfWharfId(wharfId);
        return statuses.stream().map(ResStatusDto::new).collect(Collectors.toList());
    }
}
