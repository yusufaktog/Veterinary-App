package com.aktog.yusuf.veteriner.service;

import com.aktog.yusuf.veteriner.dto.AnimalDto;
import com.aktog.yusuf.veteriner.dto.converter.AnimalDtoConverter;
import com.aktog.yusuf.veteriner.dto.request.CreateAnimalRequest;
import com.aktog.yusuf.veteriner.dto.request.UpdateAnimalRequest;
import com.aktog.yusuf.veteriner.entity.Animal;
import com.aktog.yusuf.veteriner.entity.AnimalOwner;
import com.aktog.yusuf.veteriner.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final AnimalDtoConverter animalDtoConverter;
    private final AnimalOwnerService animalOwnerService;

    public AnimalService(AnimalRepository animalRepository,
                         AnimalDtoConverter animalDtoConverter,
                         AnimalOwnerService animalOwnerService) {
        this.animalRepository = animalRepository;
        this.animalDtoConverter = animalDtoConverter;
        this.animalOwnerService = animalOwnerService;
    }

    public List<AnimalDto> filterByName(String name) {
        return getAllAnimalDtoList().
                stream().
                filter(animalDto -> animalDto.
                        getName().
                        toUpperCase().
                        contains(name.toUpperCase())).collect(Collectors.toList());
    }

    public List<AnimalDto> filterByType(String type) {
        return getAllAnimalDtoList().
                stream().
                filter(animalDto -> animalDto.
                        getType().
                        toUpperCase().
                        contains(type.toUpperCase())).collect(Collectors.toList());
    }

    public List<AnimalDto> filterByGenus(String genus) {
        return getAllAnimalDtoList().stream().
                filter(animalDto -> animalDto.
                        getGenus().
                        toUpperCase().
                        contains(genus.toUpperCase())).collect(Collectors.toList());
    }

    public AnimalDto createAnimal(CreateAnimalRequest request) {
        AnimalOwner owner = animalOwnerService.findByOwnerId(request.getOwnerId());
        Animal animal = new Animal(
                request.getType(),
                request.getGenus(),
                request.getName(),
                request.getAge(),
                request.getDescription(),
                owner
        );
        return animalDtoConverter.convert(animalRepository.save(animal));
    }

    public AnimalDto updateAnimal(String id, UpdateAnimalRequest request) {
        Animal animal = findByAnimalId(id);
        AnimalOwner owner = animalOwnerService.findByOwnerId(request.getOwnerId());
        Animal updatedAnimal = new Animal(
                animal.getId(),
                animal.getType(),
                animal.getGenus(),
                request.getName(),
                request.getAge(),
                request.getDescription(),
                owner
        );
        return animalDtoConverter.convert(animalRepository.save(updatedAnimal));
    }

    public String deleteAnimal(String id) {
        findByAnimalId(id);
        animalRepository.deleteById(id);
        return "id: " + id + " has been deleted";
    }

    public AnimalDto getAnimalById(String id) {
        return animalDtoConverter.convert(findByAnimalId(id));
    }

    public Animal findByAnimalId(String id) {
        return animalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pet id: " + id + " not found"));
    }

    public List<AnimalDto> getAllAnimalDtoList() {
        return animalDtoConverter.convert(getAllAnimalList());
    }

    public List<Animal> getAllAnimalList() {
        return animalRepository.findAll();
    }

}