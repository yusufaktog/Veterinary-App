package com.aktog.yusuf.veteriner.service;

import com.aktog.yusuf.veteriner.dto.UserDto;
import com.aktog.yusuf.veteriner.dto.converter.UserDtoConverter;
import com.aktog.yusuf.veteriner.dto.request.CreateLoginRequest;
import com.aktog.yusuf.veteriner.dto.request.CreateUserRequest;
import com.aktog.yusuf.veteriner.entity.Role;
import com.aktog.yusuf.veteriner.entity.User;
import com.aktog.yusuf.veteriner.repository.UserRepository;
import com.aktog.yusuf.veteriner.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;
    private final BCryptPasswordEncoder encoder;


    public LoginService(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            UserRepository userRepository,
            UserDtoConverter userDtoConverter,
            BCryptPasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.userDtoConverter = userDtoConverter;
        this.encoder = encoder;
    }

    public String login(CreateLoginRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword());
        authenticationManager.authenticate(token);
        return jwtUtil.generate(request.getUsername());
    }

    public UserDto register(CreateUserRequest request) {
        return userDtoConverter.convert(userRepository.save(
                new User(request.getUsername(),
                        encoder.encode(request.getPassword()), Optional.ofNullable(request.getRoles())
                        .orElse(new HashSet<>()))));
    }

    public List<UserDto> getAllUsers() {
        return userDtoConverter.convert(userRepository.findAll());
    }

    public UserDto assignRoleToUser(String username, Set<Role> roles) {
        User user = userRepository.findByUsername(username);
        user.getRoles().addAll(roles);
        User updatedUser = new User(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
        );
        return userDtoConverter.convert(userRepository.save(updatedUser));
    }

    public UserDto unAssignRoleToUser(String username, Role role) {
        User user = userRepository.findByUsername(username);
        user.getRoles().remove(role);
        User updatedUser = new User(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
        );
        return userDtoConverter.convert(userRepository.save(updatedUser));
    }
}