package com.Han2m.portLogistics.user.service;


import com.Han2m.portLogistics.exception.CustomException;
import com.Han2m.portLogistics.user.domain.Permission;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.domain.Worker;
import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
import com.Han2m.portLogistics.user.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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

    @Transactional(readOnly = true)
    public Worker find(Long personId) {
        return workerRepository.findById(personId).orElseThrow(CustomException.EntityNotFoundException::new);
    }


    @Transactional(readOnly = true)
    public List<Worker> findAllWorkerAndWharf(String name,int sort,int dir) {

        Sort.Direction direction = (dir == 1) ? Sort.Direction.ASC : Sort.Direction.DESC;

        String sortField = (sort == 0) ? "name" : "position";
        Sort dynamicSort = Sort.by(direction, sortField);

        return workerRepository.findAllWorkerWithWharf(name,dynamicSort);
    }

    public Worker registerWorker(ReqWorkerDto reqWorkerDto){

        List<Wharf> wharfList = wharfService.getWharfList(reqWorkerDto.getWharfs());

        Worker worker = reqWorkerDto.toEntity();

        permissionService.permit(worker,wharfList);

        workerRepository.save(worker);

        return worker;
    }


    public void editWorker(Long personId,ReqWorkerDto reqWorkerDto){

        Worker worker = find(personId);

        permissionService.deleteByPerson(worker);

        worker.updateWorker(reqWorkerDto);

        List<Wharf> wharfList = wharfService.getWharfList(reqWorkerDto.getWharfs());

        List<Permission> permissionList = worker.getPermissionList();

        permissionService.permit(worker,wharfList);

        worker.setPermissionList(permissionList);
    }

    public void editMe(String accountId ,ReqWorkerDto reqWorkerDto){

        Worker worker = workerRepository.findByAccountId(accountId).orElseThrow(CustomException.EntityNotFoundException::new);

        permissionService.deleteByPerson(worker);

        worker.updateWorker(reqWorkerDto);

        List<Wharf> wharfList = wharfService.getWharfList(reqWorkerDto.getWharfs());

        List<Permission> permissionList = worker.getPermissionList();

        permissionService.permit(worker,wharfList);

        worker.setPermissionList(permissionList);
    }

    public void deleteWorker(Long personId) {
        workerRepository.delete(workerRepository.findById(personId).orElseThrow(CustomException.EntityNotFoundException::new));
    }


}