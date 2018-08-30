package com.lw.dmappserver.spell;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
public class SpellController {
    private SpellRepository repo;
    private static final Logger logger = LogManager.getLogger(SpellController.class);

    public SpellController(SpellRepository repo) {
        this.repo = repo;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/spells")
    public ResponseEntity<Spell> createSpell(@RequestBody Spell newSpell,
                                             @RequestParam(name = "userId") String userId) {
        if (newSpell == null || !newSpell.getUserId().equals(userId)) {
            logger.warn("Bad request to create new spell: " + newSpell);
            return ResponseEntity.badRequest().build();
        }

        Spell tmp = repo.findSpellByNameAndUserId(newSpell.getName(), userId);
        if (tmp != null && tmp.getName().equals(newSpell.getName())) {
            logger.warn("Spell conflicts with existing spell \n\t<" + newSpell + ">");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(newSpell);
        }

        tmp = repo.insert(newSpell);
        logger.info("Creating new spell: " + newSpell);
        if (tmp.getId() != null) {
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(newSpell);
        }
    }

    @RequestMapping(method=RequestMethod.GET, value = "/spells/{id}")
    public ResponseEntity<Spell> findById(@PathVariable String id,
                                          @RequestParam(name = "userId") String userId) {
        logger.info("Finding spell for userId: " + userId + " \nwith id: " + id);
        Spell tmp = repo.findSpellById(id);
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

    @RequestMapping(method=RequestMethod.GET, value = "/spells", params = "userId")
    public ResponseEntity<List<Spell>> findByUser(@RequestParam(name = "userId") String userId) {
        logger.info("Finding spells by user: " + userId);
        List<Spell> tmp = repo.findSpellsByUserId(userId);
        if (tmp != null && tmp.size() > 0) {
            logger.info("Found " + tmp.size() + " records");
            return ResponseEntity.status(HttpStatus.FOUND).body(tmp);
        } else {
            logger.warn("Found no records belonging to user");
            return ResponseEntity.status(HttpStatus.OK).body(new LinkedList<>());
        }
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

    @RequestMapping(method = RequestMethod.PUT, value = "/spells", params = "userId")
    public ResponseEntity<Spell> updateSpell(@RequestBody Spell spellToUpdate, @RequestParam(name = "userId") String userId) {
        logger.info("Updating spell ID: " + spellToUpdate.getId() + "\t::\tName: " + spellToUpdate.getName());

        Spell current = repo.findSpellById(spellToUpdate.getId());
        if (current == null) {
            logger.warn("Unable to update spell!");
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        current = repo.save(spellToUpdate);
        if (current.getId().equals(spellToUpdate.getId())) {
            logger.info("Successful update");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(current);
        } else {
            logger.error("Unable to update spell due to internal conflict.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/spells/{id}", params = "userId")
    public ResponseEntity<Spell> deleteSpell(@PathVariable(name = "id") String spellId, @RequestParam(name = "userId") String userId) {
        logger.info("Deleting spell ID: " + spellId + "\t::\tUser: " + userId);
        Spell current = repo.findSpellById(spellId);
        if (current == null) {
            logger.warn("Spell not found");
            return ResponseEntity.notFound().build();
        }

        repo.delete(current);
        current = repo.findSpellById(spellId);
        if (current == null) {
            logger.info("Spell successfully deleted");
            return ResponseEntity.accepted().build();
        } else {
            logger.error("Unable to delete spell");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
