package com.Han2m.portLogistics.user.service;


import com.Han2m.portLogistics.exception.CustomException.EntityNotFoundException;
import com.Han2m.portLogistics.user.domain.Permission;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.domain.Worker;
import com.Han2m.portLogistics.user.dto.req.ReqWorkerDto;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import com.Han2m.portLogistics.user.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final WorkerRepository workerRepository;
    private final WharfRepository wharfRepository;

    @Transactional(readOnly = true)
    public Worker find(Long personId) {
        return workerRepository.findByIdWithWharf(personId).orElseThrow(() -> new EntityNotFoundException("해당 Worker는 존재하지 않습니다."));
    }


    @Transactional(readOnly = true)
    public List<Worker> findAllWorkerAndWharf(String name,int sort,boolean dir,int page) {

        Sort.Direction direction = dir ? Sort.Direction.ASC : Sort.Direction.DESC;

        String sortField = (sort == 0) ? "name" : "position";
        Sort dynamicSort = Sort.by(direction, sortField);

        Pageable pageable = PageRequest.of(page,10,dynamicSort);

        return workerRepository.findAllWorkerWithWharf(name,pageable).getContent();
    }

    public void registerWorker(ReqWorkerDto reqWorkerDto){

        List<Wharf> wharfList = wharfRepository.findAllById(reqWorkerDto.getWharfs());
        if(wharfList.isEmpty()){
            throw new EntityNotFoundException("해당 Wharf는 존재하지 않습니다");
        }

        Worker worker = reqWorkerDto.toEntity();

        workerRepository.save(worker);

        permissionService.permit(worker,wharfList);

    }


    public void editWorker(Long personId,ReqWorkerDto reqWorkerDto){

        Worker worker = find(personId);

        permissionService.deleteByPerson(worker);

        worker.updateWorker(reqWorkerDto);

        List<Wharf> wharfList = wharfRepository.findAllById(reqWorkerDto.getWharfs());

        List<Permission> permissionList = worker.getPermissionList();

        permissionService.permit(worker,wharfList);

        worker.setPermissionList(permissionList);
    }

    public void editMe(String accountId ,ReqWorkerDto reqWorkerDto){

        Worker worker = workerRepository.findByAccountId(accountId).orElseThrow(() -> new EntityNotFoundException("해당 ID의 계정이 존재하지 않습니다."));

        permissionService.deleteByPerson(worker);

        worker.updateWorker(reqWorkerDto);

        List<Wharf> wharfList = wharfRepository.findAllById(reqWorkerDto.getWharfs());

        List<Permission> permissionList = worker.getPermissionList();

        permissionService.permit(worker,wharfList);

        worker.setPermissionList(permissionList);
    }

    public void deleteWorker(Long personId) {
        workerRepository.delete(workerRepository.findByIdWithWharf(personId).orElseThrow(() -> new EntityNotFoundException("해당 Worker는 존재하지 않습니다.")));
    }


}