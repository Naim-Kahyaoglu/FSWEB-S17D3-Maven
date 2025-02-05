package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/koalas")
public class KoalaController {

    private Map<Integer, Koala> koalaMap = new HashMap<>();

    @PostMapping
    public ResponseEntity<Koala> saveKoala(@RequestBody Koala koala) {
        koalaMap.put(koala.getId(), koala);
        return ResponseEntity.ok(koala);
    }

    @GetMapping
    public ResponseEntity<List<Koala>> findAllKoalas() {
        return ResponseEntity.ok(koalaMap.values().stream().collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Koala> findKoalaById(@PathVariable int id) {
        Koala koala = koalaMap.get(id);
        if (koala == null) {
            throw new ZooException("Koala not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(koala);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Koala> updateKoala(@PathVariable int id, @RequestBody Koala updatedKoala) {
        Koala koala = koalaMap.get(id);
        if (koala == null) {
            throw new ZooException("Koala not found", HttpStatus.NOT_FOUND);
        }
        koala.setName(updatedKoala.getName());
        koala.setSleepHour(updatedKoala.getSleepHour());
        koala.setWeight(updatedKoala.getWeight());
        koala.setGender(updatedKoala.getGender());
        return ResponseEntity.ok(koala);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Koala> deleteKoala(@PathVariable int id) {
        Koala deletedKoala = koalaMap.remove(id);
        if (deletedKoala != null) {
            return ResponseEntity.ok(deletedKoala);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
