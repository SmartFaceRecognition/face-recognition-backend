package com.Han2m.portLogistics.user.service;

import com.Han2m.portLogistics.exception.EntityNotFoundException;
import com.Han2m.portLogistics.user.domain.Wharf;
import com.Han2m.portLogistics.user.repository.WharfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WharfService {

    private final WharfRepository wharfRepository;

    public Wharf find(Long wharfId){
        return wharfRepository.findById(wharfId).orElseThrow(EntityNotFoundException::new);
    }
}
