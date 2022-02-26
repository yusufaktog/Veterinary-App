package com.aktog.yusuf.veteriner.controller;

import com.aktog.yusuf.veteriner.dto.AnimalDto;
import com.aktog.yusuf.veteriner.dto.request.CreateAnimalRequest;
import com.aktog.yusuf.veteriner.dto.request.UpdateAnimalRequest;
import com.aktog.yusuf.veteriner.service.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/pet")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/filter/n{name}")
    public ResponseEntity<List<AnimalDto>> filterByName(@RequestParam String name){
        return ResponseEntity.ok(animalService.filterByName(name));
    }

    @GetMapping("/filter/t{type}")
    public ResponseEntity<List<AnimalDto>> filterByType(@RequestParam String type){
        return ResponseEntity.ok(animalService.filterByType(type));
    }

    @GetMapping("/filter/g{genus}")
    public ResponseEntity<List<AnimalDto>> filterByGenus(@RequestParam String genus){
        return ResponseEntity.ok(animalService.filterByGenus(genus));
    }

    @GetMapping
    public ResponseEntity<List<AnimalDto>> getAllAnimals() {
        return ResponseEntity.ok(new ArrayList<>(animalService.getAllAnimalDtoList()));
    }

    @PostMapping
    public ResponseEntity<AnimalDto> createAnimal(@Valid @RequestBody CreateAnimalRequest request) {
        return new ResponseEntity<>(animalService.createAnimal(request), HttpStatus.CREATED);
    }

    @PutMapping("/{animalId}")
    public ResponseEntity<AnimalDto> updateAnimal(@PathVariable String animalId, @Valid @RequestBody UpdateAnimalRequest request) {
        return ResponseEntity.ok(animalService.updateAnimal(animalId,request));
    }

    @DeleteMapping("/{animalId}")
    public ResponseEntity<String> deleteAnimal(@PathVariable String animalId){
        return new ResponseEntity<>(animalService.deleteAnimal(animalId),HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{animalId}")
    public ResponseEntity<AnimalDto> getAnimalById(@PathVariable String animalId){
        return  ResponseEntity.ok(animalService.getAnimalById(animalId));
    }
}