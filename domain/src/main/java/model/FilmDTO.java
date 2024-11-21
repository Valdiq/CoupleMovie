package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FilmDTO(@JsonProperty("Title") String title, @JsonProperty("Year") String year,
                      @JsonProperty("imdbID") String imdbId, @JsonProperty("Type") String  type,
                      @JsonProperty("Poster") String poster) {
}
