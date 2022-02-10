package com.aktog.yusuf.veteriner.dto.converter;

import com.aktog.yusuf.veteriner.dto.AnimalDto;
import com.aktog.yusuf.veteriner.dto.AnimalOwnerDto;
import com.aktog.yusuf.veteriner.entity.Animal;
import com.aktog.yusuf.veteriner.entity.AnimalOwner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AnimalOwnerDtoConverter {

    public AnimalOwnerDto convert(AnimalOwner from){
        return new AnimalOwnerDto(
                from.getId(),
                from.getName(),
                from.getSurname(),
                from.getEmail(),
                from.getPhoneNumber(),
                getReducedAnimalDtoList(new ArrayList<>(Objects.requireNonNull(from.getPets())))
        );
    }
    public List<AnimalOwnerDto> convert(List<AnimalOwner> from){
        return from.stream().map(this::convert).collect(Collectors.toList());
    }
    private List<AnimalDto> getReducedAnimalDtoList(List<Animal> from) {

        return from.stream().map(
                f -> new AnimalDto(
                        f.getId(),
                        f.getType(),
                        f.getGenus(),
                        f.getName(),
                        f.getAge(),
                        f.getDescription()
                )
        ).collect(Collectors.toList());
    }
}