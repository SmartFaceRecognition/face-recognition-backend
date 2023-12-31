package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.user.domain.Person;
import com.Han2m.portLogistics.user.domain.Permission;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public void deleteByPerson(Person person){
        permissionRepository.deleteByPerson(person);
    }

    public void permit(Person person,List<Wharf> wharfList) {
        for(Wharf wharf: wharfList){
            Permission permission = new Permission(person,wharf);
            person.getPermissionList().add(permission);
        }
    }
}
