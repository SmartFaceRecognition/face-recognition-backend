package com.Han2m.portLogistics.user.service;


import com.Han2m.portLogistics.user.domain.Person;
import com.Han2m.portLogistics.user.domain.Status;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StatusService {

    private final StatusRepository statusRepository;
    private final PersonService personService;
    private final WharfService wharfService;

    public void registerWorkerEnter(Long id, Long wharfId){
        Person person = personService.find(id);
        Wharf wharf = wharfService.find(wharfId);

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

    public void registerWorkerOut(Long id, Long wharfId){
        Person person = personService.find(id);
        Wharf wharf = wharfService.find(wharfId);

        Optional<Status> status = statusRepository.findByPersonAndWharf(person, wharf);
        if(status.isPresent()){
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            status.get().updateOutTime(currentTime);
        }
    }


    @Transactional(readOnly = true)
    public List<Status> findWorkerInWharf(Long wharfId){
        return statusRepository.findByOutTimeIsNullAndWharfWharfId(wharfId);
    }
}
