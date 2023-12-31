package com.Han2m.portLogistics.user.service;


import com.Han2m.portLogistics.exception.EntityNotFoundException;
import com.Han2m.portLogistics.user.domain.Permission;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.domain.Worker;
import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
import com.Han2m.portLogistics.user.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WorkerService {

    private final PermissionService permissionService;
    private final WharfService wharfService;
    private final WorkerRepository workerRepository;

    // Worker 조회
    @Transactional(readOnly = true)
    public Worker find(Long personId) {
        return workerRepository.findById(personId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Worker> findAllWorkerAndWharf() {
        return workerRepository.findAllWorkerWithWharf();
    }

    public void deleteWorker(Long personId) {
        workerRepository.delete(workerRepository.findById(personId).orElseThrow(EntityNotFoundException::new));
    }

    public Worker registerWorker(ReqWorkerDto reqWorkerDto){

        List<Wharf> wharfList = reqWorkerDto.getWharfs().stream().map(wharfService::find).toList();

        //Dto to entity
        Worker worker = reqWorkerDto.toEntity();

        permissionService.permit(worker,wharfList);

        workerRepository.save(worker);

        return worker;
    }


    public Worker editWorker(Long personId,ReqWorkerDto reqWorkerDto){

        Worker worker = find(personId);

        permissionService.deleteByPerson(worker);

        worker.updateWorker(reqWorkerDto);

        List<Wharf> wharfList = reqWorkerDto.getWharfs().stream().map(wharfService::find).toList();

        List<Permission> permissionList = worker.getPermissionList();

        permissionService.permit(worker,wharfList);

        worker.setPermissionList(permissionList);

       return worker;
    }

    // Worker 삭제



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