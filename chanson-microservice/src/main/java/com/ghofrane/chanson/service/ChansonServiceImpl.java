package com.ghofrane.chanson.service;

import com.ghofrane.chanson.dto.APIResponseDto;
import com.ghofrane.chanson.dto.ChansonDto;
import com.ghofrane.chanson.dto.ChansonMessageDto;
import com.ghofrane.chanson.dto.GenreDto;
import com.ghofrane.chanson.entities.Chanson;
import com.ghofrane.chanson.exceptions.ResourceNotFoundException;
import com.ghofrane.chanson.repos.ChansonRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChansonServiceImpl implements ChansonService {

    private static final Logger log = LoggerFactory.getLogger(ChansonServiceImpl.class);

    private ChansonRepository chansonRepository;
    private APIClient apiClient;
    private StreamBridge streamBridge;

    @Override
    public APIResponseDto getChansonById(Long id) {
        Chanson chanson = chansonRepository.findById(id).get();

        GenreDto genreDto = apiClient.getGenreByCode(chanson.getGenreCode());

        String genreNom = (genreDto == null) ? "NOT AVAILABLE" : genreDto.getNom();

        ChansonDto chansonDto = new ChansonDto(
                chanson.getId(),
                chanson.getTitre(),
                chanson.getArtiste(),
                chanson.getGenreCode(),
                genreNom,
                chanson.getEmail(),
                chanson.getMobileNumber()
        );

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setChansonDto(chansonDto);
        apiResponseDto.setGenreDto(genreDto);

        return apiResponseDto;
    }

    @Override
    public void createChanson(ChansonDto chansonDto) {
        Chanson savedChanson = chansonRepository.save(Chanson.builder()
                .titre(chansonDto.getTitre())
                .artiste(chansonDto.getArtiste())
                .genreCode(chansonDto.getGenreCode())
                .email(chansonDto.getEmail())
                .mobileNumber(chansonDto.getMobileNumber())
                .build());
        sendCommunication(savedChanson);
    }

    private void sendCommunication(Chanson chanson) {
        var chansonMsgDto = new ChansonMessageDto(chanson.getId(), chanson.getEmail(), chanson.getMobileNumber());
        log.info("Sending Communication request for the details: {}", chansonMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", chansonMsgDto);
        log.info("Is the Communication request successfully triggered ? : {}", result);
    }

    @Override
    public boolean updateCommunicationStatus(Long chansonId) {
        boolean isUpdated = false;
        if (chansonId != null) {
            Chanson chanson = chansonRepository.findById(chansonId).orElseThrow(
                    () -> new ResourceNotFoundException("Chanson", "ChansonId", chansonId.toString())
            );
            chanson.setCommunicationDone(true);
            chansonRepository.save(chanson);
            isUpdated = true;
        }
        return isUpdated;
    }
}