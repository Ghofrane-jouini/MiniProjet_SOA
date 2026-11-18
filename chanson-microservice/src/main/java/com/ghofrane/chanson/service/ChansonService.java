package com.ghofrane.chanson.service;

import com.ghofrane.chanson.dto.APIResponseDto;
import com.ghofrane.chanson.dto.ChansonDto;

public interface ChansonService {
    APIResponseDto getChansonById(Long id);

    void createChanson(ChansonDto chansonDto);

    boolean updateCommunicationStatus(Long chansonId);
}