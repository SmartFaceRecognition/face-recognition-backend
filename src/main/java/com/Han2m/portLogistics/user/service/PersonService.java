package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.user.dto.GuestDto;
import com.Han2m.portLogistics.user.dto.PersonDto;
import com.Han2m.portLogistics.user.dto.WorkerDto;
import com.Han2m.portLogistics.user.entity.*;
import com.Han2m.portLogistics.user.repository.PersonRepository;
import com.Han2m.portLogistics.user.repository.PersonWharfRepository;
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
import java.util.stream.Collectors;


@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final WharfRepository wharfRepository;
    private final PersonWharfRepository personWharfRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, @Qualifier("wharfRepository") WharfRepository wharfRepository,
                         PersonWharfRepository personWharfRepository) {
        this.personRepository = personRepository;
        this.wharfRepository = wharfRepository;
        this.personWharfRepository = personWharfRepository;
    }

    // Worker 조회
    public WorkerDto getWorkerById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("직원을 찾을 수 없습니다. ID: " + id));

        if (!(person instanceof Worker)) {
            throw new IllegalStateException("해당 아이디의 사용자는 Worker가 아닙니다.");
        }

        Worker worker = (Worker) person;
        WorkerDto workerDto = new WorkerDto();
        workerDto.setId(worker.getId());
        workerDto.setIsWorker(worker.getIsWorker());
        workerDto.setNationality(worker.getNationality());
        workerDto.setName(worker.getName());
        workerDto.setBirth(worker.getBirth());
        workerDto.setSex(worker.getSex());
        workerDto.setPhone(worker.getPhone());
        workerDto.setPosition(worker.getPosition());
        workerDto.setFaceUrl(worker.getFaceUrl());
        workerDto.setFingerPrint(worker.getFingerprint());

        List<PersonWharf> personWharfList = personWharfRepository.findByPersonId(worker.getId());
        List<Wharf> wharfList = personWharfList.stream()
                .map(PersonWharf::getWharf)
                .collect(Collectors.toList());

        List<String> wharfs = wharfList.stream()
                .map(Wharf::getPlace)
                .collect(Collectors.toList());
        workerDto.setWharfs(wharfs);

        return workerDto;
    }

    // Guest 조회
    public GuestDto getGuestById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("손님을 찾을 수 없습니다. ID: " + id));

        if (!(person instanceof Guest)) {
            throw new IllegalStateException("해당 아이디의 사용자는 Guest가 아닙니다.");
        }

        Guest guest = (Guest) person;
        GuestDto guestDto = new GuestDto();
        guestDto.setId(guest.getId());
        guestDto.setIsWorker(guest.getIsWorker());
        guestDto.setNationality(guest.getNationality());
        guestDto.setName(guest.getName());
        guestDto.setBirth(guest.getBirth());
        guestDto.setSex(guest.getSex());
        guestDto.setPhone(guest.getPhone());
        guestDto.setSsn(guest.getSsn());

        List<PersonWharf> personWharfList = personWharfRepository.findByPersonId(guest.getId());
        List<Wharf> wharfList = personWharfList.stream()
                .map(PersonWharf::getWharf)
                .collect(Collectors.toList());

        List<String> wharfs = wharfList.stream()
                .map(Wharf::getPlace)
                .collect(Collectors.toList());
        guestDto.setWharfs(wharfs);

        return guestDto;
    }



    // Worker 등록
    @Transactional
    public WorkerDto registerWorker(WorkerDto workerDto) {
        Worker worker = new Worker();
        worker.setNationality(workerDto.getNationality());
        worker.setName(workerDto.getName());
        worker.setSex(workerDto.getSex());
        worker.setBirth(workerDto.getBirth());
        worker.setPhone(workerDto.getPhone());
        worker.setPosition(workerDto.getPosition());
        worker.setFaceUrl(workerDto.getFaceUrl());
        worker.setFingerprint(workerDto.getFingerPrint());
        worker.setIsWorker(true);

        Worker savedWorker = personRepository.save(worker);

        if (workerDto.getWharfs() != null) {
            saveWorkerWharfs(workerDto.getWharfs(), savedWorker);
        }
        return convertToWorkerDTO(savedWorker);
    }

    // Guest 등록
    @Transactional
    public GuestDto registerGuest(GuestDto guestDto) {
        Guest guest = new Guest();
        guest.setNationality(guestDto.getNationality());
        guest.setName(guestDto.getName());
        guest.setSex(guestDto.getSex());
        guest.setBirth(guestDto.getBirth());
        guest.setPhone(guestDto.getPhone());
        guest.setSsn(guestDto.getSsn());
        guest.setIsWorker(false);

        Guest savedGuest = personRepository.save(guest);

        if (guestDto.getWharfs() != null) {
            saveGuestWharfs(guestDto.getWharfs(), savedGuest);
        }
        return convertToGuestDTO(savedGuest);
    }

    // 부두 정보 저장하기 -- 직원용
    private void saveWorkerWharfs(List<String> wharfs, Worker savedWorker) {
        List<String> uniqueWharfs = wharfs.stream().distinct().collect(Collectors.toList());
        for (String wharfPlace : uniqueWharfs) {
            Wharf wharf;
            List<Wharf> existingWharfs = wharfRepository.findByPlace(wharfPlace);
            if (existingWharfs.isEmpty()) {
                wharf = new Wharf();
                wharf.setPlace(wharfPlace);
                wharf = wharfRepository.save(wharf);
            } else {
                wharf = existingWharfs.get(0);
            }

            PersonWharf personWharf = new PersonWharf(savedWorker, wharf);
            personWharfRepository.save(personWharf);

            savedWorker.getPersonWharfList().add(personWharf);
            wharf.getPersonWharfList().add(personWharf);
        }
    }


    // 부두 정보 저장하기 -- 손님용
    private void saveGuestWharfs(List<String> wharfs, Guest savedGuest) {
        List<String> uniqueWharfs = wharfs.stream().distinct().collect(Collectors.toList());
        for (String wharfPlace : uniqueWharfs) {
            Wharf wharf;
            List<Wharf> existingWharfs = wharfRepository.findByPlace(wharfPlace);
            if (existingWharfs.isEmpty()) {
                wharf = new Wharf();
                wharf.setPlace(wharfPlace);
                wharf = wharfRepository.save(wharf);
            } else {
                wharf = existingWharfs.get(0);
            }

            PersonWharf personWharf = new PersonWharf(savedGuest, wharf);
            personWharfRepository.save(personWharf);

            savedGuest.getPersonWharfList().add(personWharf);
            wharf.getPersonWharfList().add(personWharf);
        }
    }


    // Worker 정보 수정하기
    public WorkerDto editWorkerInfo(Long id, WorkerDto updatedWorkerDTO) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 직원이 없습니다."));

        if (!(person instanceof Worker)) {
            throw new IllegalStateException("해당 아이디의 사용자는 Worker가 아닙니다.");
        }

        Worker worker = (Worker) person;
        worker.setNationality(updatedWorkerDTO.getNationality());
        worker.setName(updatedWorkerDTO.getName());
        worker.setBirth(updatedWorkerDTO.getBirth());
        worker.setSex(updatedWorkerDTO.getSex());
        worker.setPhone(updatedWorkerDTO.getPhone());
        worker.setPosition(updatedWorkerDTO.getPosition());
        worker.setFaceUrl(updatedWorkerDTO.getFaceUrl());
        worker.setFingerprint(updatedWorkerDTO.getFingerPrint());

        Worker updatedWorker = (Worker) personRepository.save(worker);

        return convertToWorkerDTO(updatedWorker);
    }

    // Guest 정보 수정하기
    public GuestDto editGuestInfo(Long id, GuestDto updatedGuestDTO) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 손님이 없습니다."));

        if (!(person instanceof Guest)) {
            throw new IllegalStateException("해당 아이디의 사용자는 Guest가 아닙니다.");
        }

        Guest guest = (Guest) person;
        guest.setNationality(updatedGuestDTO.getNationality());
        guest.setName(updatedGuestDTO.getName());
        guest.setBirth(updatedGuestDTO.getBirth());
        guest.setSex(updatedGuestDTO.getSex());
        guest.setPhone(updatedGuestDTO.getPhone());
        guest.setSsn(updatedGuestDTO.getSsn());

        Guest updatedGuest = (Guest) personRepository.save(guest);

        return convertToGuestDTO(updatedGuest);
    }


    // 인원 삭제
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }


    // 페이징
    public Page<PersonDto> getAllPersons(Pageable pageable) {
        Page<Person> persons = personRepository.findAll(pageable);
        return persons.map(person -> person instanceof Worker ? convertToWorkerDTO((Worker) person) : convertToGuestDTO((Guest) person));
    }

    // 등록순(id 오름차순)으로 정렬하기
    public Page<PersonDto> getAllPersonsOrderByRegistrationDate(Pageable pageable) {
        Page<Person> persons = personRepository.findAll(pageable);
        return persons.map(person -> person instanceof Worker ? convertToWorkerDTO((Worker) person) : convertToGuestDTO((Guest) person));
    }

    // 이름으로 검색
    public List<PersonDto> searchPersonByName(String name) {
        List<Person> persons = personRepository.findByName(name);
        return persons.stream()
                .map(person -> person instanceof Worker ? convertToWorkerDTO((Worker) person) : convertToGuestDTO((Guest) person))
                .collect(Collectors.toList());
    }


    private Worker convertToWorkerEntity(WorkerDto workerDto) {
        Worker worker = new Worker();
        worker.setNationality(workerDto.getNationality());
        worker.setName(workerDto.getName());
        worker.setBirth(workerDto.getBirth());
        worker.setSex(workerDto.getSex());
        worker.setPhone(workerDto.getPhone());
        worker.setPosition(workerDto.getPosition());
        worker.setFaceUrl(workerDto.getFaceUrl());
        worker.setFingerprint(workerDto.getFingerPrint());

        // 부두 관련
        setPersonWharfList(worker, workerDto.getWharfs());

        return worker;
    }

    private Guest convertToGuestEntity(GuestDto guestDto) {
        Guest guest = new Guest();
        guest.setNationality(guestDto.getNationality());
        guest.setName(guestDto.getName());
        guest.setBirth(guestDto.getBirth());
        guest.setSex(guestDto.getSex());
        guest.setPhone(guestDto.getPhone());
        guest.setSsn(guestDto.getSsn());

        // 부두 관련
        setPersonWharfList(guest, guestDto.getWharfs());

        return guest;
    }

    private void setPersonWharfList(Person person, List<String> wharfPlaces) {
        List<PersonWharf> personWharfList = new ArrayList<>();
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
    }

    private WorkerDto convertToWorkerDTO(Worker worker) {
        WorkerDto workerDto = new WorkerDto();
        workerDto.setId(worker.getId());
        workerDto.setNationality(worker.getNationality());
        workerDto.setName(worker.getName());
        workerDto.setIsWorker(worker.getIsWorker());
        workerDto.setBirth(worker.getBirth());
        workerDto.setSex(worker.getSex());
        workerDto.setPhone(worker.getPhone());
        workerDto.setPosition(worker.getPosition());
        workerDto.setFaceUrl(worker.getFaceUrl());
        workerDto.setFingerPrint(worker.getFingerprint());

        List<String> wharfPlaces = worker.getPersonWharfList().stream()
                .map(PersonWharf::getWharf)
                .map(Wharf::getPlace)
                .collect(Collectors.toList());
        workerDto.setWharfs(wharfPlaces);
        return workerDto;
    }

    private GuestDto convertToGuestDTO(Guest guest) {
        GuestDto guestDto = new GuestDto();
        guestDto.setId(guest.getId());
        guestDto.setNationality(guest.getNationality());
        guestDto.setName(guest.getName());
        guestDto.setBirth(guest.getBirth());
        guestDto.setIsWorker(guest.getIsWorker());
        guestDto.setSex(guest.getSex());
        guestDto.setPhone(guest.getPhone());
        guestDto.setSsn(guest.getSsn());

        List<String> wharfPlaces = guest.getPersonWharfList().stream()
                .map(PersonWharf::getWharf)
                .map(Wharf::getPlace)
                .collect(Collectors.toList());
        guestDto.setWharfs(wharfPlaces);
        return guestDto;
    }



    // 테스트용 랜덤부두, 인스턴스 생성시 아래 메소드 자동 호출
    @PostConstruct
    public void createTestWharfs() {
        Wharf wharf1 = new Wharf(1L, "1wharf");
        Wharf wharf2 = new Wharf(2L, "2wharf");
        Wharf wharf3 = new Wharf(3L, "3wharf");
        Wharf wharf4 = new Wharf(4L, "4wharf");
        Wharf wharf5 = new Wharf(5L, "5wharf");
        wharfRepository.saveAll(List.of(wharf1, wharf2, wharf3, wharf4, wharf5));
    }
}
