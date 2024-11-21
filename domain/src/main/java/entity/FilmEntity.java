package entity;

import lombok.Data;
import lombok.experimental.Accessors;

import org.springframework.data.annotation.Id;

@Data
@Accessors(chain = true)
public class FilmEntity {

    @Id
    private Long id;

    private String title;

    private String year;

    private String imdbId;

    private String type;

    private String poster;

}
