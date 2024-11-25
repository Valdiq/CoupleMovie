package org.example.logging.repository;

import org.example.logging.document.ServiceExceptionLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceExceptionLogRepository extends MongoRepository<ServiceExceptionLogDocument, String> {
}
