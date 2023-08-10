package com.Han2m.portLogistics.user.service;


import com.Han2m.portLogistics.user.dto.res.ResStatusDto;
import com.Han2m.portLogistics.user.dto.res.ResWorkerDto;
import com.Han2m.portLogistics.user.entity.Status;
import com.Han2m.portLogistics.user.entity.Wharf;
import com.Han2m.portLogistics.user.entity.Worker;
import com.Han2m.portLogistics.user.repository.StatusRepository;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import com.Han2m.portLogistics.user.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StatusService {

    private final StatusRepository statusRepository;
    private final WorkerRepository workerRepository;
    private final WharfRepository wharfRepository;

    public ResStatusDto registerWorkerEnter(Long id, Long wharfId){
        Worker worker = workerRepository.findById(id).get();
        Wharf wharf = wharfRepository.findById(wharfId).get();

        // 입장 시간 (현재 시간)
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        Status status = new Status();

        status.setWorker(worker);
        status.setEnterTime(currentTime);
        status.setWharf(wharf);

        statusRepository.save(status);

        return new ResStatusDto(status);
    }

    public ResStatusDto registerWorkerOut(Long id, Long wharfId){

        Status status = statusRepository.findLastStatusWithNullOutTimeByWharfAndWorker(id,wharfId);

        // 입장 시간 (현재 시간)
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        status.setOutTime(currentTime);

        statusRepository.save(status);

        return new ResStatusDto(status);
    }



    public List<ResStatusDto> getAllWorkerInWharf(){
        List<Status> statuses = statusRepository.findByOutTimeIsNull();
        return statuses.stream().map(ResStatusDto::new).collect(Collectors.toList());
    }

    public List<ResStatusDto> getWorkerInWharf(Long wharfId){
        List<Status> statuses = statusRepository.findByOutTimeIsNullAndWharfWharfId(wharfId);
        return statuses.stream().map(ResStatusDto::new).collect(Collectors.toList());
    }


}
