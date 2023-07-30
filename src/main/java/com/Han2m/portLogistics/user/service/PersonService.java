package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.user.dto.GuestDto;
import com.Han2m.portLogistics.user.dto.PersonDto;
import com.Han2m.portLogistics.user.dto.WorkerDto;
import com.Han2m.portLogistics.user.entity.*;
import com.Han2m.portLogistics.user.repository.PersonRepository;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import com.amazonaws.services.kms.model.NotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final WharfRepository wharfRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, @Qualifier("wharfRepository") WharfRepository wharfRepository) {
        this.personRepository = personRepository;
        this.wharfRepository = wharfRepository;
    }


    // 직원 조회
    public PersonDto getPersonById(Long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            PersonDto personDto = convertToPersonDTO(person);

            List<Wharf> wharfList = wharfRepository.findByPersonWharfListPersonId(person.getId());
            List<String> wharfs = wharfList.stream()
                    .map(Wharf::getPlace)
                    .collect(Collectors.toList());
            personDto.setWharfs(wharfs);

            return personDto;
        } else {
            throw new EntityNotFoundException("직원을 찾을 수 없습니다. ID: " + id);
        }
    }

    @Transactional
    public PersonDto registerPerson(PersonDto personDto) {

        if (personDto.getWharfs() != null) {
            List<String> uniqueWharfs = personDto.getWharfs().stream().distinct().collect(Collectors.toList());
            for (String wharf : uniqueWharfs) {
                if (!wharfRepository.existsByPlace(wharf)) {
                    Wharf newWharf = new Wharf();
                    newWharf.setPlace(wharf);
                    wharfRepository.save(newWharf);

                    PersonWharf personWharfEntity = new PersonWharf(savedPerson, newWharf);
                    savedPerson.getPersonWharfList().add(personWharfEntity);
                    newWharf.getPersonWharfList().add(personWharfEntity);
                }
            }
        }
        // additional code to set Guest or Worker entity...
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

        List<PersonWharf> updatedPersonWharves = new ArrayList<>();
        List<String> updatedWharfPlaces = updatedPersonDTO.getWharfs();
        if (updatedWharfPlaces != null) {
            for (String place : updatedWharfPlaces) {
                List<Wharf> wharfEntities = wharfRepository.findByPlace(place);
                if (!wharfEntities.isEmpty()) {
                    Wharf wharfEntity = wharfEntities.get(0);
                    PersonWharf personWharf = new PersonWharf(person, wharfEntity);
                    updatedPersonWharves.add(personWharf);
                }
            }
        }
        person.setPersonWharfList(updatedPersonWharves);

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
        person.setIsWorker(personDto.getIsWorker());
        person.setSex(personDto.getSex());
        person.setPhone(personDto.getPhone());


        //GuestDto나 WorkerDto가 null이 아닌 경우에만 엔티티를 생성하고 설정
        if(personDto.getGuest() != null) {
            Guest guest = new Guest();
            guest.setSsn(personDto.getGuest().getSsn());
            guest.setAddress(personDto.getGuest().getAddress());
            person.setGuest(guest);
        }

        if(personDto.getWorker() != null) {
            Worker worker = new Worker();
            worker.setPosition(personDto.getWorker().getPosition());
            worker.setFaceUrl(personDto.getWorker().getFaceUrl());
            worker.setFingerprint(personDto.getWorker().getFingerPrint());
            person.setWorker(worker);
        }

        // 부두 관련
        List<PersonWharf> personWharfList = new ArrayList<>();
        List<String> wharfPlaces = personDto.getWharfs();
        if (wharfPlaces != null) {
            for (String place : wharfPlaces) {
                List<Wharf> wharfEntities = wharfRepository.findByPlace(place);
                if (!wharfEntities.isEmpty()) {
                    Wharf wharfEntity = wharfEntities.get(0);

                    PersonWharf personWharf = new PersonWharf(person, wharfEntity);
                    personWharfList.add(personWharf);
                }
            }
        }
        person.setPersonWharfList(personWharfList);
        return person;
    }

    private PersonDto convertToPersonDTO(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setNationality(person.getNationality());
        personDto.setName(person.getName());
        personDto.setBirth(person.getBirth());
        personDto.setPhone(person.getPhone());

        // Convert related entities to DTOs
        if(person.getGuest() != null) {
            GuestDto guestDto = new GuestDto();
            guestDto.setSsn(person.getGuest().getSsn());
            guestDto.setAddress(person.getGuest().getAddress());
            personDto.setGuest(guestDto);
        }

        if(person.getWorker() != null) {
            WorkerDto workerDto = new WorkerDto();
            workerDto.setPosition(person.getWorker().getPosition());
            workerDto.setFaceUrl(person.getWorker().getFaceUrl());
            workerDto.setFingerPrint(person.getWorker().getFingerprint());
            personDto.setWorker(workerDto);
        }

        List<String> wharfPlaces = person.getPersonWharfList().stream()
                .map(PersonWharf::getWharf)
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
