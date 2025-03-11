package com.elev8.backend.registration.repository;

import com.elev8.backend.registration.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, java.lang.String> {
    Optional<User> findByUsername(java.lang.String username);
    boolean existsByUsername(java.lang.String username);
    List<User> findByCollegeName(java.lang.String collegeName);
    Optional<User> findById(java.lang.String id);

    List<User> findAllById(List<User> userid);
}