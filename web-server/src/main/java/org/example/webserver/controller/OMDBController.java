package org.example.webserver.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.ExpandedFilmDTO;
import org.example.domain.model.PreviewFilmDTO;
import org.example.domain.model.PreviewFilmResponse;
import org.example.domain.service.ExpandedFilmService;
import org.example.domain.service.PreviewFilmService;
import org.example.logging.log_target.LogExecutionTime;
import org.example.security.roleaccess.AnyRoleAccess;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OMDBController {

    private final PreviewFilmService previewFilmService;
    private final ExpandedFilmService expandedFilmService;

    @AnyRoleAccess
    @LogExecutionTime
    @GetMapping("/search")
    public List<PreviewFilmResponse> searchFilm(@RequestParam String title, @RequestParam(required = false) String type, @RequestParam(required = false) String year, @RequestParam(required = false) Integer page) {
        return previewFilmService.searchFilms(title, type, year, page);
    }

    @AnyRoleAccess
    @LogExecutionTime
    @GetMapping("/searchByEmotions")
    public List<PreviewFilmDTO> searchFilmsByEmotions(@RequestBody List<String> emotions) {
        return previewFilmService.searchFilmsByEmotions(emotions);
    }

    @AnyRoleAccess
    @LogExecutionTime
    @GetMapping("/get")
    public ExpandedFilmDTO getFilm(@RequestParam String imdbId) {
        return expandedFilmService.getFilm(imdbId);
    }

    @AnyRoleAccess
    @LogExecutionTime
    @GetMapping("/get/random")
    public ExpandedFilmDTO getRandomFilm() {
        return expandedFilmService.getRandomFilm();
    }

}
