package com.ghofrane.chanson.service;

import com.ghofrane.chanson.dto.GenreDto;
import org.springframework.stereotype.Component;

@Component
public class GenreFallBack implements APIClient {

    @Override
    public GenreDto getGenreByCode(String genreCode) {
        return null;
    }
}