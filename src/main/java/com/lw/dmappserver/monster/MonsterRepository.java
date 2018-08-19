package com.lw.dmappserver.monster;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "monsters", path = "monsters")
public interface MonsterRepository extends MongoRepository<Monster, String> {
    Monster findMonsterById(String _id);
    Monster findMonsterByName(String name);
    List<Monster> findMonstersByNameContaining(String substr);
    List<Monster> findMonstersByNameContainingAndUserId(String substr, String userId);
    List<Monster> findMonstersByUserId(String userId);
    Long deleteMonsterById(String id);
}
