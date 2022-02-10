package com.aktog.yusuf.veteriner.service;

import com.aktog.yusuf.veteriner.dto.AnimalDto;
import com.aktog.yusuf.veteriner.dto.AnimalOwnerDto;
import com.aktog.yusuf.veteriner.dto.converter.AnimalOwnerDtoConverter;
import com.aktog.yusuf.veteriner.dto.request.CreateAnimalOwnerRequest;
import com.aktog.yusuf.veteriner.dto.request.UpdateAnimalOwnerRequest;
import com.aktog.yusuf.veteriner.entity.AnimalOwner;
import com.aktog.yusuf.veteriner.repository.AnimalOwnerRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalOwnerService {
    private final AnimalOwnerRepository animalOwnerRepository;
    private final AnimalOwnerDtoConverter animalOwnerDtoConverter;

    public AnimalOwnerService(AnimalOwnerRepository animalOwnerRepository, AnimalOwnerDtoConverter animalOwnerDtoConverter) {
        this.animalOwnerRepository = animalOwnerRepository;
        this.animalOwnerDtoConverter = animalOwnerDtoConverter;
    }

    public List<AnimalOwnerDto> filterByName(String name) {
        return getAllAnimalOwnerDtoList().
                stream().
                filter(animalOwnerDto -> animalOwnerDto.
                        getName().
                        toUpperCase().
                        contains(name.toUpperCase())).collect(Collectors.toList());
    }

    public List<AnimalOwnerDto> filterBySurname(String surname) {
        return getAllAnimalOwnerDtoList().
                stream().
                filter(a -> a.
                        getSurname().
                        toUpperCase().
                        contains(surname.toUpperCase())).collect(Collectors.toList());
    }

    public AnimalOwnerDto createAnimalOwner(CreateAnimalOwnerRequest request) {
        AnimalOwner animalOwner = new AnimalOwner(
                request.getName(),
                request.getSurname(),
                request.getEmail(),
                request.getPhoneNumber()
        );
        return animalOwnerDtoConverter.convert(animalOwnerRepository.save(animalOwner));
    }

    public AnimalOwnerDto updateAnimalOwner(String id, UpdateAnimalOwnerRequest request) {
        AnimalOwner owner = findByOwnerId(id);
        AnimalOwner updatedOwner = new AnimalOwner(
                owner.getId(),
                request.getName(),
                request.getSurname(),
                request.getEmail(),
                request.getPhoneNumber()
        );
        return animalOwnerDtoConverter.convert(animalOwnerRepository.save(updatedOwner));

    }

    public String deleteAnimalOwner(String id) {
        findByOwnerId(id);
        animalOwnerRepository.deleteById(id);
        return "id: " + id + " has been deleted";
    }

    public AnimalOwnerDto getOwnerById(String id) {
        return animalOwnerDtoConverter.convert(findByOwnerId(id));
    }

    public AnimalOwner findByOwnerId(String id) {
        return animalOwnerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Owner id: " + id + "not found"));
    }

    public List<AnimalOwnerDto> getAllAnimalOwnerDtoList() {
        return animalOwnerDtoConverter.convert(getAllAnimalOwnerList());
    }

    public List<AnimalOwner> getAllAnimalOwnerList() {
        return animalOwnerRepository.findAll();
    }

}