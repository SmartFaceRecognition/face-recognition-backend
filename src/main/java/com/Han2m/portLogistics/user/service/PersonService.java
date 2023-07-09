package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.user.dto.PersonDto;
import com.Han2m.portLogistics.user.entity.PersonEntity;
import com.Han2m.portLogistics.user.repository.PersonRepository;
import com.amazonaws.services.kms.model.NotFoundException;
import org.springframework.stereotype.Service;


@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    // 직원 등록
    public PersonDto registerPerson(PersonDto personDto) {
        PersonEntity personEntity = convertToPersonEntity(personDto);
        PersonEntity savedPersonEntity = personRepository.save(personEntity);
        return convertToPersonDTO(savedPersonEntity);
    }


    // 직원 고유 id get
    public PersonDto getPersonById(Long id) {
        PersonEntity personEntity = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person not found"));
        return convertToPersonDTO(personEntity);
    }

    // 직원 정보 수정하기
    public PersonDto editPersonInfo(Long id, PersonDto updatedPersonDTO) {
        PersonEntity personEntity = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person not found"));
        personEntity.setNationality(updatedPersonDTO.getNationality());
        personEntity.setName(updatedPersonDTO.getName());
        personEntity.setBirth(updatedPersonDTO.getBirth());
        personEntity.setPhone(updatedPersonDTO.getPhone());
        personEntity.setPosition(updatedPersonDTO.getPosition());
        personEntity.setFaceUrl(updatedPersonDTO.getFaceUrl());
        PersonEntity updatedPersonEntity = personRepository.save(personEntity);
        return convertToPersonDTO(updatedPersonEntity);
    }

    // 직원 삭제 (출입 불가)
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    private PersonEntity convertToPersonEntity(PersonDto PersonDto) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setNationality(PersonDto.getNationality());
        personEntity.setName(PersonDto.getName());
        personEntity.setBirth(PersonDto.getBirth());
        personEntity.setPhone(PersonDto.getPhone());
        personEntity.setPosition(PersonDto.getPosition());
        personEntity.setFaceUrl(PersonDto.getFaceUrl());
        return personEntity;
    }

    private PersonDto convertToPersonDTO(PersonEntity personEntity) {
        PersonDto personDTO = new PersonDto();
        personDTO.setId(personEntity.getId());
        personDTO.setNationality(personEntity.getNationality());
        personDTO.setName(personEntity.getName());
        personDTO.setBirth(personEntity.getBirth());
        personDTO.setPhone(personEntity.getPhone());
        personDTO.setPosition(personEntity.getPosition());
        personDTO.setFaceUrl(personEntity.getFaceUrl());
        return personDTO;
    }
}
