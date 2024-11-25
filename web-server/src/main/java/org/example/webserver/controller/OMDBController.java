package org.example.webserver.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.FilmResponse;
import org.example.domain.service.FilmService;
import org.example.logging.log_target.LogExecutionTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OMDBController {

    private final FilmService service;

    @LogExecutionTime
    @GetMapping("/search")
    public Flux<FilmResponse> searchFilm(@RequestParam(required = true) String title, @RequestParam(required = false) String type, @RequestParam(required = false) String year, @RequestParam(required = false) Integer page) {
        return service.searchFilms(title, type, year, page);
    }

}
