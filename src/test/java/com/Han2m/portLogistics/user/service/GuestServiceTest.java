package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.user.domain.PersonWharf;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.domain.Worker;
import com.Han2m.portLogistics.user.dto.req.ReqGuestDto;
import com.Han2m.portLogistics.user.dto.res.ResGuestDto;
import com.Han2m.portLogistics.user.repository.GuestRepository;
import com.Han2m.portLogistics.user.repository.PersonWharfRepository;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import com.Han2m.portLogistics.user.repository.WorkerRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GuestServiceTest {

    @InjectMocks
    private GuestService guestService;
    @Mock
    private GuestRepository guestRepository;
    @Mock
    private WharfRepository wharfRepository;
    @Mock
    private WorkerRepository workerRepository;
    @Mock
    private PersonWharfRepository personWharfRepository;

    @Before
    void registerWharf(){
        Wharf wharf1 = new Wharf(1L,"제 1 부두");
        Wharf wharf2 = new Wharf(2L,"제 2 부두");
        Wharf wharf3 = new Wharf(3L,"제 3 부두");

        when(wharfRepository.findById(1L)).thenReturn(Optional.of(wharf1));
        when(wharfRepository.findById(2L)).thenReturn(Optional.of(wharf2));
        when(wharfRepository.findById(3L)).thenReturn(Optional.of(wharf3));
    }

    @Test
    @DisplayName("게스트 등록")
    void registerGuest() {
        //given
        ReqGuestDto req = ReqGuestDto.builder().
                nationality("대한민국").
                name("김철수").
                sex(true).
                birth("990402").
                phone("01012345678").
                date(LocalDate.parse("2023-12-05")).
                reason("체험").
                goal("견학").
                workerId(1L).
                wharfs(Stream.of(1L,2L,3L).collect(Collectors.toList())).
                build();

        Worker worker = new Worker();
        Wharf wharf1 = new Wharf(1L,"제 1 부두");
        Wharf wharf2 = new Wharf(2L,"제 2 부두");
        Wharf wharf3 = new Wharf(3L,"제 3 부두");
        PersonWharf personWharf1 = new PersonWharf(req.toEntity(),wharf1);

        //when
        when(wharfRepository.findById(1L)).thenReturn(Optional.of(wharf1));
        when(wharfRepository.findById(2L)).thenReturn(Optional.of(wharf2));
        when(wharfRepository.findById(3L)).thenReturn(Optional.of(wharf3));
        when(workerRepository.findById(anyLong())).thenReturn(Optional.of(worker));
        when(personWharfRepository.save(any(PersonWharf.class))).thenReturn(personWharf1);

        //then
        ResGuestDto res = ResGuestDto.builder().
                nationality("대한민국").
                name("김철수").
                sex(true).
                birth("990402").
                phone("01012345678").
                personId(1L).
                date(LocalDate.parse("2023-12-05")).
                reason("체험").
                goal("견학").
                workerId(1L).
                wharfs( new ArrayList<>(Stream.of("제 1 부두", "제 2 부두", "제 3 부두").collect(Collectors.toList()))).
                build();


        Assertions.assertEquals(res, guestService.registerGuest(req));
    }

    @Test
    @DisplayName("id를 통한 게스트 객체 가져오기")
    void getGuestById() {
        //given
        //when
        //then
    }

    @Test
    @DisplayName("게스트 제거")
    void deleteGuest() {
        //given
        //when
        //then
    }
}