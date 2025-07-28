package org.example.domain.repository.mongo;

import org.example.domain.document.GenreEmotionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreEmotionRepository extends MongoRepository<GenreEmotionDocument, String> {

    List<GenreEmotionDocument> findByEmotionsIn(List<String> emotions);
}
