package org.example.security.repository;

import org.example.security.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends R2dbcRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
