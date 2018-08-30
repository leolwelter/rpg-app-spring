package com.lw.dmappserver.spell;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

// annotation not strictly necessary, only to change export defaults
@RepositoryRestResource(collectionResourceRel = "spells", path = "spells")
public interface SpellRepository extends MongoRepository<Spell , String> {
    Spell findSpellById(String id);
    List<Spell> findSpellsByUserId(String userId);
    List<Spell> findSpellsByNameContainingAndUserId(String substr, String userId);
    Spell findSpellByName(String name);
    Spell findSpellByNameAndUserId(String name, String userId);
}
