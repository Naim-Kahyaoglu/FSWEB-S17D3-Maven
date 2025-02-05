package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {

    private Map<Integer, Kangaroo> kangarooMap = new HashMap<>();

    @PostMapping
    public ResponseEntity<Kangaroo> saveKangaroo(@RequestBody Kangaroo kangaroo) {
        if (kangaroo.getName() == null || kangaroo.getGender() == null) {
            throw new RuntimeException("Invalid Kangaroo: name and gender are required");
        }
        kangarooMap.put(kangaroo.getId(), kangaroo);
        return ResponseEntity.ok(kangaroo);
    }

    @GetMapping
    public ResponseEntity<List<Kangaroo>> findAllKangaroos() {
        List<Kangaroo> kangaroos = kangarooMap.values().stream().collect(Collectors.toList());
        return ResponseEntity.ok(kangaroos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kangaroo> findKangarooById(@PathVariable int id) {
        Kangaroo kangaroo = kangarooMap.get(id);
        if (kangaroo == null) {
            throw new ZooException("Kangaroo not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(kangaroo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kangaroo> updateKangaroo(@PathVariable int id, @RequestBody Kangaroo updatedKangaroo) {
        Kangaroo kangaroo = kangarooMap.get(id);
        if (kangaroo == null) {
            throw new ZooException("Kangaroo not found", HttpStatus.NOT_FOUND);
        }
        kangaroo.setName(updatedKangaroo.getName());
        kangaroo.setHeight(updatedKangaroo.getHeight());
        kangaroo.setWeight(updatedKangaroo.getWeight());
        kangaroo.setGender(updatedKangaroo.getGender());
        kangaroo.setIsAggressive(updatedKangaroo.getIsAggressive());
        return ResponseEntity.ok(kangaroo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Kangaroo> deleteKangaroo(@PathVariable int id) {
        Kangaroo deletedKangaroo = kangarooMap.remove(id);
        if (deletedKangaroo != null) {
            return ResponseEntity.ok(deletedKangaroo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
