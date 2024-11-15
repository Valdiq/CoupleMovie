package model;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("Film")
public record CachedFilmEntity(Long id, String title, String year, String imdbId, String type, String poster) {
}
