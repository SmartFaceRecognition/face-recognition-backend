package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.user.domain.Person;
import com.Han2m.portLogistics.user.domain.PersonWharf;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.repository.PersonWharfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonWharfService {

    private final PersonWharfRepository personWharfRepository;

    public void deleteByPerson(Person person){
        personWharfRepository.deleteByPerson(person);
    }

    public void matchPersonWharf(Person person,List<Wharf> wharfList) {
        for(Wharf wharf: wharfList){
            PersonWharf personWharf = new PersonWharf(person,wharf);
            person.getPersonWharfList().add(personWharf);
        }
    }
}
