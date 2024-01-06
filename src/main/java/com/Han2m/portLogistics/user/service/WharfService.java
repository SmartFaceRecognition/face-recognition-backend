package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.exception.CustomException;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WharfService {

    private final WharfRepository wharfRepository;

    public List<Wharf> getWharfList(List<Long> idList){
        return idList.stream()
                .map(wharfRepository::findById)
                .map(optional -> optional.orElseThrow(CustomException.EntityNotFoundException::new))
                .toList();

    }
}
