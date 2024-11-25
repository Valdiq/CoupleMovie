package org.example.logging.repository;

import org.example.logging.document.ExecutionTimeLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionTimeLogRepository extends MongoRepository<ExecutionTimeLogDocument, String> {
}
