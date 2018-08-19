package com.lw.dmappserver.user;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// annotation not strictly necessary, only to change export defaults
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends MongoRepository<User, String> {
    User findUserBy_id(String id);
    User findUserByUsername(String username);
}

