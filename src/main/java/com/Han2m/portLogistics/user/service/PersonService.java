package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.user.dto.PersonDto;
import com.Han2m.portLogistics.user.entity.PersonEntity;
import com.Han2m.portLogistics.user.entity.User_Wharf_Entity;
import com.Han2m.portLogistics.user.entity.WharfEntity;
import com.Han2m.portLogistics.user.repository.PersonRepository;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import com.amazonaws.services.kms.model.NotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    
    @Autowired
    private WharfRepository wharfRepository;


    // 직원 조회
    public PersonDto getPersonById(Long id) {
        Optional<PersonEntity> optionalPersonEntity = personRepository.findById(id);
        if (optionalPersonEntity.isPresent()) {
            PersonEntity personEntity = optionalPersonEntity.get();
            PersonDto personDto = convertToPersonDTO(personEntity);

            List<User_Wharf_Entity> userWharfEntities = personEntity.getUserWharfEntityList();
            List<String> wharfs = userWharfEntities.stream()
                    .map(User_Wharf_Entity::getWharfEntity)
                    .map(WharfEntity::getPlace)
                    .collect(Collectors.toList());
            personDto.setWharfs(wharfs);

            return personDto;
        } else {
            throw new EntityNotFoundException("직원을 찾을 수 없습니다. ID: " + id);
        }
    }

    // 직원 등록
    public PersonDto registerPerson(PersonDto personDto) {
        PersonEntity personEntity = convertToPersonEntity(personDto);
        PersonEntity savedPersonEntity = personRepository.save(personEntity);

        if (personDto.getWharfs() != null) {
            List<String> wharfs = personDto.getWharfs();
            for (String wharf : wharfs) {
                List<WharfEntity> wharfEntities = wharfRepository.findByPlace(wharf);
                if (!wharfEntities.isEmpty()) {
                    WharfEntity wharfEntity = wharfEntities.get(0);

                    User_Wharf_Entity userWharfEntity = new User_Wharf_Entity(savedPersonEntity, wharfEntity);
                    savedPersonEntity.getUserWharfEntityList().add(userWharfEntity);
                }
            }
        }
        return convertToPersonDTO(savedPersonEntity);
    }


    // 직원 정보 수정하기
    public PersonDto editPersonInfo(Long id, PersonDto updatedPersonDTO) {
        PersonEntity personEntity = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 직원이 없습니다."));
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



    // 수정이 필요할 수도
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


    // 테스트용 랜덤부두, 인스턴스 생성시 아래 메소드 자동 호출
    @PostConstruct
    public void createTestWharfs() {
        WharfEntity wharf1 = new WharfEntity(1L, "제 1부두");
        WharfEntity wharf2 = new WharfEntity(2L,"제 2부두");
        WharfEntity wharf3 = new WharfEntity(3L,"제 3부두");
        wharfRepository.saveAll(List.of(wharf1, wharf2, wharf3));
    }
}
