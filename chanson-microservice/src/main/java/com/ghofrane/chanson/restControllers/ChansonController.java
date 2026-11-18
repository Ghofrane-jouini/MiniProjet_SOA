package com.ghofrane.chanson.restControllers;

import com.ghofrane.chanson.dto.APIResponseDto;
import com.ghofrane.chanson.dto.ChansonDto;
import com.ghofrane.chanson.service.ChansonService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RefreshScope
@RequestMapping("/api/chansons")
@RequiredArgsConstructor
public class ChansonController {

    private final ChansonService chansonService;

    @GetMapping("{id}")
    public ResponseEntity<APIResponseDto> getChansonById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(chansonService.getChansonById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createChanson(@RequestBody ChansonDto chansonDto) {
        chansonService.createChanson(chansonDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Chanson created successfully");
    }
}