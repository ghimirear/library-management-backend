package com.library.library.controller;

import com.library.library.entity.User;
import com.library.library.model.Login;
import com.library.library.model.LoginResult;
import com.library.library.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService= userService;
    }
    @GetMapping
    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("this backend is working ");
    }
    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody User user){

        return userService.saveUser(user);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResult> loginUser(@RequestBody Login login){
        return userService.loginUser(login);
    }
}
