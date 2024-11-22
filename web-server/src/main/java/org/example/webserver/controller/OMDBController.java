package org.example.webserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.FilmResponse;
import reactor.core.publisher.Flux;
import org.example.domain.service.FilmService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OMDBController {
    
    private final FilmService service;

    @GetMapping("/search")
    public Flux<FilmResponse> searchFilm(@RequestParam(required = true) String title, @RequestParam(required = false) String type, @RequestParam(required = false) String year, @RequestParam(required = false) Integer page) {
        return service.searchFilms(title, type, year, page);
    }

}
