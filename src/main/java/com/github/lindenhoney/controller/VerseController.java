package com.github.lindenhoney.controller;

import com.github.lindenhoney.domain.Verse;
import com.github.lindenhoney.mapper.VerseMapper;
import com.github.lindenhoney.repository.VerseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verses")
@RequiredArgsConstructor
public class VerseController {

    private final VerseRepository repository;
    private final VerseMapper mapper;

    @GetMapping("/search/random")
    public ResponseEntity<Verse> getRandomVerse() {
        return ResponseEntity.of(repository
                .findRandomVerse()
                .map(mapper::toDomain)
        );
    }
}
