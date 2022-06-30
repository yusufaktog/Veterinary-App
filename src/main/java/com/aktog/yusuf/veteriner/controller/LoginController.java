package com.aktog.yusuf.veteriner.controller;

import com.aktog.yusuf.veteriner.dto.UserDto;
import com.aktog.yusuf.veteriner.dto.request.CreateLoginRequest;

import com.aktog.yusuf.veteriner.dto.request.CreateUserRequest;
import com.aktog.yusuf.veteriner.entity.Role;
import com.aktog.yusuf.veteriner.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CreateLoginRequest request){
        return ResponseEntity.ok(loginService.login(request));
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserRequest request){
        return ResponseEntity.ok(loginService.register(request));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(loginService.getAllUsers());
    }

    @PostMapping("/role/{username}")
    public ResponseEntity<UserDto> assignRoleToUser(@PathVariable String username,@RequestBody Set<Role> roles){
        return ResponseEntity.ok(loginService.assignRoleToUser(username,roles));
    }

    @PutMapping("/role/{username}")
    public ResponseEntity<UserDto> unAssignRoleToUser(@PathVariable String username,@RequestBody Role role){
        return ResponseEntity.ok(loginService.unAssignRoleToUser(username,role));
    }
}