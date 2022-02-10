package com.aktog.yusuf.veteriner.controller;

import com.aktog.yusuf.veteriner.dto.AnimalDto;
import com.aktog.yusuf.veteriner.dto.AnimalOwnerDto;
import com.aktog.yusuf.veteriner.dto.request.CreateAnimalOwnerRequest;
import com.aktog.yusuf.veteriner.dto.request.CreateAnimalRequest;
import com.aktog.yusuf.veteriner.dto.request.UpdateAnimalOwnerRequest;
import com.aktog.yusuf.veteriner.service.AnimalOwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/owner")
public class AnimalOwnerController {
    private final AnimalOwnerService ownerService;

    public AnimalOwnerController(AnimalOwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public ResponseEntity<List<AnimalOwnerDto>> getAllAnimalOwners() {
        return ResponseEntity.ok(new ArrayList<>(ownerService.getAllAnimalOwnerDtoList()));
    }

    @GetMapping("/filter/name{name}")
    public ResponseEntity<List<AnimalOwnerDto>> filterByName(@RequestParam String name){
        return ResponseEntity.ok(ownerService.filterByName(name));
    }

    @GetMapping("/filter/surname{name}")
    public ResponseEntity<List<AnimalOwnerDto>> filterBySurname(@RequestParam String surname){
        return ResponseEntity.ok(ownerService.filterBySurname(surname));
    }

    @PostMapping
    public ResponseEntity<AnimalOwnerDto> createAnimalOwner(@Valid @RequestBody CreateAnimalOwnerRequest request) {
        return new ResponseEntity<>(ownerService.createAnimalOwner(request), HttpStatus.CREATED);
    }

    @PutMapping("/{ownerId}")
    public ResponseEntity<AnimalOwnerDto> updateOwner(@PathVariable String ownerId,
                                                      @Valid @RequestBody UpdateAnimalOwnerRequest request) {
        return ResponseEntity.ok(ownerService.updateAnimalOwner(ownerId, request));

    }

    @DeleteMapping("/{ownerId}")
    public ResponseEntity<String> deleteOwner(@PathVariable String ownerId) {
        return new ResponseEntity<>(ownerService.deleteAnimalOwner(ownerId), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<AnimalOwnerDto> getOwnerById(@PathVariable String ownerId) {
        return ResponseEntity.ok(ownerService.getOwnerById(ownerId));
    }
}