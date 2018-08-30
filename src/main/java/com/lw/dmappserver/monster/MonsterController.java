package com.lw.dmappserver.monster;

import com.lw.dmappserver.factory.ServiceFactory;
import com.lw.dmappserver.service.ExportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RestController
public class MonsterController {
    private MonsterRepository repo;
    private static final Logger logger = LogManager.getLogger(MonsterController.class);

    public MonsterController(MonsterRepository repo) {
        this.repo = repo;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/monsters", params = "userId")
    public ResponseEntity<Monster> createMonster(@RequestParam String userId,
                                                 @RequestBody Monster toCreate) {
        if (userId == null || userId.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Monster());

        repo.insert(toCreate);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(toCreate);
    }

    @RequestMapping(value ="/monsters/all")
    public List<Monster> findAll() {
        List<Monster> tmp = repo.findAll();
        logger.info("Found " + tmp.size() + " records");
        return tmp;
    }

    @RequestMapping(method=RequestMethod.GET, value = "/monsters/{id}")
    public Monster findById(@PathVariable String id) {
        logger.info("Finding monster: " + id);
        Monster tmp = repo.findMonsterById(id);
        return tmp;
    }

    @RequestMapping(method=RequestMethod.GET, value = "/monsters", params = {"name", "userId"})
    public List<Monster> searchByName(@RequestParam(name = "name") String name,
                                      @RequestParam(name = "userId") String userId) {
        if (userId != null && name != null && !name.isEmpty()) {
            List<Monster> tmp = repo.findMonstersByNameContainingAndUserId(name, userId);
            logger.info("Finding monsters with name containing: " + name);
            return tmp;
        }
        return null;
    }


    @RequestMapping(method=RequestMethod.GET, value = "/monsters", params = "userId")
    public List<Monster> searchByUser(@RequestParam(name = "userId") String userId) {
        List<Monster> tmp = repo.findMonstersByUserId(userId);
        logger.info("Finding monster by user: " + userId);
        return tmp;
    }


    @RequestMapping(method=RequestMethod.GET, value = "/monsters/export/{id}")
    public ResponseEntity<byte[]> exportMonster(@PathVariable String id) throws Exception {
        logger.info("Exporting monster by id:" + id);
        Monster tmp = repo.findMonsterById(id);
        if (tmp == null) {
            return null;
        }

        // export to pdf
        byte[] data = ServiceFactory.createExportService().exportToPdf(tmp);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Content-disposition", "attachment; filename=monster-" + tmp.getName() + ".pdf")
                .header("Content-type", "application/pdf")
                .body(data);
    }

    @RequestMapping(method=RequestMethod.GET, value = "/monsters/export", params = "userId")
    public ResponseEntity<byte[]> exportAllMonstersByUser(@RequestParam String userId) throws Exception {
        logger.info("Exporting monsters by user Id:" + userId);
        LinkedList<Monster> tmp = new LinkedList<>(repo.findMonstersByUserId(userId));
        if (tmp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body( new byte[]{} );
        }

        // export to pdf
        byte[] data = ServiceFactory.createExportService().exportToPdf(tmp);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Content-disposition", "attachment; filename=monsters-" + userId + ".pdf")
                .header("Content-type", "application/pdf")
                .body(data);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/monsters", params = "userId")
    public ResponseEntity<Monster> saveMonster(@RequestParam String userId, @RequestBody Monster monster) {
        if (userId == null || userId.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else if (monster == null || !userId.equals(monster.getUserId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        logger.info("Saving changes to monster:" + monster.getId());
        Monster tmp = repo.save(monster);
        return ResponseEntity.status(HttpStatus.FOUND).body(tmp);
    }

    @RequestMapping(method=RequestMethod.DELETE, value = "/monsters/{id}", params = "userId")
    public ResponseEntity<Monster> deleteMonsterById(@RequestParam String userId, @PathVariable String id) {
        if (userId == null || userId.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Monster());

        logger.info("Deleting monster by Id:" + id);
        Long tmp = repo.deleteMonsterById(id);
        if (tmp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Monster());
        }

        return ResponseEntity.status(HttpStatus.OK).body(new Monster());
    }
}
