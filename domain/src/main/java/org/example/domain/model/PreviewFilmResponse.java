package org.example.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PreviewFilmResponse(@JsonProperty("Search") List<PreviewFilmDTO> search,
                                  @JsonProperty("totalResults") String totalResults,
                                  @JsonProperty("Response") Boolean response) {
    public PreviewFilmResponse {
        search = List.copyOf(search);
    }

    @Override
    public List<PreviewFilmDTO> search() {
        return List.copyOf(search);
    }
}
