package org.example.logging.repository;

import org.example.logging.document.ControllerExceptionLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControllerExceptionLogRepository extends MongoRepository<ControllerExceptionLogDocument, String> {
}
