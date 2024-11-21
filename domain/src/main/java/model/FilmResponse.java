package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record FilmResponse(@JsonProperty("Search") List<FilmDTO> search,
                           @JsonProperty("totalResults") String totalResults,
                           @JsonProperty("Response") Boolean response) {
    public FilmResponse {
        search = List.copyOf(search);
    }

    @Override
    public List<FilmDTO> search() {
        return List.copyOf(search);
    }
}
