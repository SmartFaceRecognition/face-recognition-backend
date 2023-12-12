package com.Han2m.portLogistics.user.controller;


import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.dto.req.ReqGuestDto;
import com.Han2m.portLogistics.user.dto.res.ResGuestDto;
import com.Han2m.portLogistics.user.repository.GuestRepository;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import com.Han2m.portLogistics.user.service.GuestService;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GuestController.class)
class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GuestService guestService;
    @MockBean
    private WharfRepository wharfRepository;
    @MockBean
    private GuestRepository guestRepository;

    @Before
    public void setup(){
        wharfRepository.save(new Wharf(1L,"제 1 부두"));
        wharfRepository.save(new Wharf(2L,"제 2 부두"));
        wharfRepository.save(new Wharf(3L,"제 3 부두"));
    }

    @Test
    @DisplayName("게스트 등록")
    @WithMockUser
    public void guest_register() throws Exception {

        // Request 값
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

        // Response 값
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
                wharfs(Stream.of("제 1 부두","제 2 부두","제 3 부두").collect(Collectors.toList())).
                build();

        // guestService의 registerGuest 메서드가 호출될 때의 동작 설정
        when(guestService.registerGuest(any(ReqGuestDto.class))).thenReturn(res);

        // POST 요청 실행
        mockMvc.perform(post("/guest/register").with(csrf()).
                contentType(MediaType.APPLICATION_JSON).
                content(TestUtil.asJsonString(req))).
                andExpect(status().isOk()).
                andExpect(content().json(TestUtil.asJsonString(res)));
    }

    @Test
    @DisplayName("게스트 정보 수정")
    @WithMockUser
    public void guest_edit() throws Exception {
        ReqGuestDto data = ReqGuestDto.builder().
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

        guestRepository.save(data.toEntity());

        ReqGuestDto req = ReqGuestDto.builder().
                nationality("대한민국").
                name("김철수").
                sex(true).
                birth("990402").
                phone("01012345678").
                date(LocalDate.parse("2023-12-04")).
                reason("실습").
                goal("견학").
                workerId(1L).
                wharfs(Stream.of(1L,2L).collect(Collectors.toList())).
                build();

        // Response 값
        ResGuestDto res = ResGuestDto.builder().
                nationality("대한민국").
                name("김철수").
                sex(true).
                birth("990402").
                phone("01012345678").
                personId(1L).
                date(LocalDate.parse("2023-12-04")).
                reason("실습").
                goal("견학").
                workerId(1L).
                wharfs(Stream.of("제 1 부두","제 2 부두").collect(Collectors.toList())).
                build();

        // guestService의 registerGuest 메서드가 호출될 때의 동작 설정
        when(guestService.editGuestInfo(1L,req)).thenReturn(res);

        // POST 요청 실행
        mockMvc.perform(post("/guest/register").with(csrf()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(TestUtil.asJsonString(req))).
                andExpect(status().isOk()).
                andExpect(content().json(TestUtil.asJsonString(res)));
    }



}
