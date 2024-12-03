package org.example.webserver.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.ExpandedFilmDTO;
import org.example.domain.model.PreviewFilmDTO;
import org.example.domain.model.PreviewFilmResponse;
import org.example.domain.service.ExpandedFilmService;
import org.example.domain.service.PreviewFilmService;
import org.example.logging.log_target.LogExecutionTime;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OMDBController {

    private final PreviewFilmService previewFilmService;
    private final ExpandedFilmService expandedFilmService;

    @LogExecutionTime
    @GetMapping("/search")
    public Flux<PreviewFilmResponse> searchFilm(@RequestParam(required = true) String title, @RequestParam(required = false) String type, @RequestParam(required = false) String year, @RequestParam(required = false) Integer page) {
        return previewFilmService.searchFilms(title, type, year, page);
    }

    @LogExecutionTime
    @GetMapping("/searchByEmotions")
    public Flux<PreviewFilmDTO> searchFilmsByEmotions(@RequestBody List<String> emotions) {
        return previewFilmService.searchFilmsByEmotions(emotions);
    }

    @LogExecutionTime
    @GetMapping("/get")
    public Mono<ExpandedFilmDTO> getFilm(@RequestParam String imdbId) {
        return expandedFilmService.getFilm(imdbId);
    }

    @LogExecutionTime
    @GetMapping("/get/random")
    public Mono<ExpandedFilmDTO> getRandomFilm() {
        return expandedFilmService.getRandomFilm();
    }

}
