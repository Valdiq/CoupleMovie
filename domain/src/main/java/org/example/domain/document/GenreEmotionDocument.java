package org.example.domain.document;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Accessors(chain = true)
@Document("genre_emotion")
public class GenreEmotionDocument {

    @Id
    private String id;

    private String genre;

    private List<String> emotions;

}
