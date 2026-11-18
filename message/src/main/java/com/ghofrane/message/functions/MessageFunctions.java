package com.ghofrane.message.functions;

import com.ghofrane.message.dto.ChansonMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<ChansonMessageDto, ChansonMessageDto> email() {
        return chansonMsgDto -> {
            log.info("Sending email with the details : {}", chansonMsgDto);
            return chansonMsgDto;
        };
    }

    @Bean
    public Function<ChansonMessageDto, Long> sms() {
        return chansonMsgDto -> {
            log.info("Sending sms with the details : {}", chansonMsgDto);
            return chansonMsgDto.chansonId();
        };
    }
}