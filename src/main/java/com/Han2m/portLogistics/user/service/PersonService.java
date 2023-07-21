package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.user.dto.PersonDto;
import com.Han2m.portLogistics.user.entity.Person;
import com.Han2m.portLogistics.user.entity.UserWharf;
import com.Han2m.portLogistics.user.entity.Wharf;
import com.Han2m.portLogistics.user.repository.PersonRepository;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import com.amazonaws.services.kms.model.NotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            PersonDto personDto = convertToPersonDTO(person);

            List<Wharf> wharfList = wharfRepository.findByUserWharfListPersonId(person.getId());
            List<String> wharfs = wharfList.stream()
                    .map(Wharf::getPlace)
                    .collect(Collectors.toList());
            personDto.setWharfs(wharfs);

            return personDto;
        } else {
            throw new EntityNotFoundException("직원을 찾을 수 없습니다. ID: " + id);
        }
    }


    // 직원 등록
    public PersonDto registerPerson(PersonDto personDto) {
        Person person = convertToPersonEntity(personDto);
        Person savedPerson = personRepository.save(person);

        if (personDto.getWharfs() != null) {
            List<String> uniqueWharfs = personDto.getWharfs().stream().distinct().collect(Collectors.toList());
            for (String wharf : uniqueWharfs) {
                if (!wharfRepository.existsByPlace(wharf)) {
                    Wharf newWharf = new Wharf();
                    newWharf.setPlace(wharf);
                    wharfRepository.save(newWharf);

                    UserWharf userWharfEntity = new UserWharf(savedPerson, newWharf);
                    savedPerson.getUserWharfList().add(userWharfEntity);
                    newWharf.getUserWharfList().add(userWharfEntity);
                }
            }
        }
        return convertToPersonDTO(savedPerson);
    }


    // 직원 정보 수정하기
    public PersonDto editPersonInfo(Long id, PersonDto updatedPersonDTO) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 직원이 없습니다."));
        person.setNationality(updatedPersonDTO.getNationality());
        person.setName(updatedPersonDTO.getName());
        person.setBirth(updatedPersonDTO.getBirth());
        person.setPhone(updatedPersonDTO.getPhone());
        person.setPosition(updatedPersonDTO.getPosition());
        person.setFaceUrl(updatedPersonDTO.getFaceUrl());

        List<UserWharf> updatedUserWharfs = new ArrayList<>();
        List<String> updatedWharfPlaces = updatedPersonDTO.getWharfs();
        if (updatedWharfPlaces != null) {
            for (String place : updatedWharfPlaces) {
                List<Wharf> wharfEntities = wharfRepository.findByPlace(place);
                if (!wharfEntities.isEmpty()) {
                    Wharf wharfEntity = wharfEntities.get(0);
                    UserWharf userWharf = new UserWharf(person, wharfEntity);
                    updatedUserWharfs.add(userWharf);
                }
            }
        }
        person.setUserWharfList(updatedUserWharfs);

        Person updatedPerson = personRepository.save(person);
        return convertToPersonDTO(updatedPerson);
    }

    // 직원 삭제
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }


    // 페이징
    public Page<PersonDto> getAllPersons(Pageable pageable) {
        Page<Person> persons = personRepository.findAll(pageable);
        return persons.map(this::convertToPersonDTO);
    }

    
    // 등록순(id 오름차순)으로 정렬하기
    public Page<PersonDto> getAllPersonsOrderByRegistrationDate(Pageable pageable) {
        Page<Person> persons = personRepository.findAll(pageable);
        return persons.map(this::convertToPersonDTO);
    }


    // 이름으로 검색
    public List<PersonDto> searchPersonByName(String name) {
        List<Person> persons = personRepository.findByName(name);
        return persons.stream()
                .map(this::convertToPersonDTO)
                .collect(Collectors.toList());
    }

    private Person convertToPersonEntity(PersonDto personDto) {
        Person person = new Person();
        person.setNationality(personDto.getNationality());
        person.setName(personDto.getName());
        person.setBirth(personDto.getBirth());
        person.setPhone(personDto.getPhone());
        person.setPosition(personDto.getPosition());
        person.setFaceUrl(personDto.getFaceUrl());

        // 부두 관련
        List<UserWharf> userWharfs = new ArrayList<>();
        List<String> wharfPlaces = personDto.getWharfs();
        if (wharfPlaces != null) {
            for (String place : wharfPlaces) {
                List<Wharf> wharfEntities = wharfRepository.findByPlace(place);
                if (!wharfEntities.isEmpty()) {
                    Wharf wharfEntity = wharfEntities.get(0);

                    UserWharf userWharf = new UserWharf(person, wharfEntity);
                    userWharfs.add(userWharf);
                }
            }
        }
        person.setUserWharfList(userWharfs);
        return person;
    }

    private PersonDto convertToPersonDTO(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setNationality(person.getNationality());
        personDto.setName(person.getName());
        personDto.setBirth(person.getBirth());
        personDto.setPhone(person.getPhone());
        personDto.setPosition(person.getPosition());
        personDto.setFaceUrl(person.getFaceUrl());

        List<String> wharfPlaces = person.getUserWharfList().stream()
                .map(UserWharf::getWharf)
                .map(Wharf::getPlace)
                .collect(Collectors.toList());
        personDto.setWharfs(wharfPlaces);

        return personDto;
    }


    // 테스트용 랜덤부두, 인스턴스 생성시 아래 메소드 자동 호출
    @PostConstruct
    public void createTestWharfs() {
        Wharf wharf1 = new Wharf(1L, "제 1부두");
        Wharf wharf2 = new Wharf(2L, "제 2부두");
        Wharf wharf3 = new Wharf(3L, "제 3부두");
        Wharf wharf4 = new Wharf(4L, "제 4부두");
        Wharf wharf5 = new Wharf(5L, "제 5부두");
        wharfRepository.saveAll(List.of(wharf1, wharf2, wharf3, wharf4, wharf5));
    }

}
