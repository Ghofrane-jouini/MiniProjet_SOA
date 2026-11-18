package com.ghofrane.chanson.functions;

import com.ghofrane.chanson.service.ChansonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ChansonFunctions {

    private static final Logger log = LoggerFactory.getLogger(ChansonFunctions.class);

    @Bean
    public Consumer<Long> updateCommunication(ChansonService chansonService) {
        return chansonId -> {
            log.info("Updating Communication status for the chanson Id : {}", chansonId);
            chansonService.updateCommunicationStatus(chansonId);
        };
    }
}