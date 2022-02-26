package com.aktog.yusuf.veteriner.dto.converter;

import com.aktog.yusuf.veteriner.dto.AnimalDto;
import com.aktog.yusuf.veteriner.dto.UserDto;
import com.aktog.yusuf.veteriner.entity.Animal;
import com.aktog.yusuf.veteriner.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoConverter {
    public UserDto convert(User from) {
        return new UserDto(
                from.getId(),
                from.getUsername(),
                from.getPassword(),
                from.getRoles()

        );
    }
    public List<UserDto> convert(List<User> from){
        return from.stream().map(this::convert).collect(Collectors.toList());
    }
}