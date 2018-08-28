package com.lw.dmappserver.spell;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpellController {
    private SpellRepository repo;
    private static final Logger logger = LogManager.getLogger(SpellController.class);

    public SpellController(SpellRepository repo) {
        this.repo = repo;
    }


    @RequestMapping(method=RequestMethod.GET, value = "/spells/{id}")
    public ResponseEntity<Spell> findById(@PathVariable String id) {
        logger.info("Finding spell: " + id);
        Spell tmp = repo.findSpellById(id);
        if (tmp != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(tmp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(tmp);
        }
    }

    @RequestMapping(method=RequestMethod.GET, value = "/spells", params = "userId")
    public ResponseEntity<Spell> findByUser(@RequestParam(name = "userId") String userId) {
        logger.info("Finding spells by user: " + userId);
        Spell tmp = repo.findSpellsByUserId(userId);
        if (tmp != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(tmp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(tmp);
        }
    }

    @RequestMapping(method=RequestMethod.GET, value = "/spells", params = {"name", "userId"})
    public List<Spell> searchByName(@RequestParam(name = "name") String name,
                                      @RequestParam(name = "userId") String userId) {
        if (userId != null && name != null && !name.isEmpty()) {
            List<Spell> tmp = repo.findSpellsByNameContainingAndUserId(name, userId);
            logger.info("Finding spells with name containing: " + name);
            return tmp;
        }
        return null;
    }

    @RequestMapping(method=RequestMethod.GET, value = "/spells/all")
    public ResponseEntity<List<Spell>> findAll() {
        logger.info("Getting all spells");
        List<Spell> tmp = repo.findAll();
        if (tmp.size() != 0) {
            return ResponseEntity.status(HttpStatus.FOUND).body(tmp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(tmp);
        }
    }
}
