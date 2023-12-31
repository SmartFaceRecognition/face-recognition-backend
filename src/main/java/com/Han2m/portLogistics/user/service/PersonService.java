package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.exception.EntityNotFoundException;
import com.Han2m.portLogistics.user.domain.Person;
import com.Han2m.portLogistics.user.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person find(Long personId){
        return personRepository.findById(personId).orElseThrow(EntityNotFoundException::new);
    }

}
