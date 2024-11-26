package org.example.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExpandedFilmDTO(
        @JsonProperty("Title") String title,
        @JsonProperty("Year") String year,
        @JsonProperty("Released") String released,
        @JsonProperty("Runtime") String runtime,
        @JsonProperty("Genre") String genre,
        @JsonProperty("Director") String director,
        @JsonProperty("Writer") String writer,
        @JsonProperty("Actors") String actors,
        @JsonProperty("Plot") String plot,
        @JsonProperty("Language") String language,
        @JsonProperty("Country") String country,
        @JsonProperty("Awards") String awards,
        @JsonProperty("Poster") String poster,
        @JsonProperty("imdbRating") String imdbRating,
        @JsonProperty("imdbVotes") String imdbVotes,
        @JsonProperty("imdbID") String imdbId,
        @JsonProperty("Type") String type
) {
}