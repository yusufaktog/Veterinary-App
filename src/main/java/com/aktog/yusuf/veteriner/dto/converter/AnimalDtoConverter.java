package com.aktog.yusuf.veteriner.dto.converter;

import com.aktog.yusuf.veteriner.dto.AnimalDto;
import com.aktog.yusuf.veteriner.dto.AnimalOwnerDto;
import com.aktog.yusuf.veteriner.entity.Animal;
import com.aktog.yusuf.veteriner.entity.AnimalOwner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AnimalDtoConverter {
    public AnimalDto convert(Animal from) {
        return new AnimalDto(
                from.getId(),
                from.getType(),
                from.getGenus(),
                from.getName(),
                from.getAge(),
                from.getDescription(),
                getReducedOwnerDtoList(from.getOwner())

        );
    }

    public List<AnimalDto> convert(List<Animal> from) {
        return from.stream().map(this::convert).collect(Collectors.toList());
    }

    private AnimalOwnerDto getReducedOwnerDtoList(AnimalOwner owner) {

        return new AnimalOwnerDto(
                owner.getId(),
                owner.getName(),
                owner.getSurname(),
                owner.getEmail(),
                owner.getPhoneNumber());
    }

}