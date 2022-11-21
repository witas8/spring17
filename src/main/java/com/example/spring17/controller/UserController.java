package com.example.spring17.controller;


import com.example.spring17.exceptions.BadRequestException;
import com.example.spring17.model.User;
import com.example.spring17.repository.UserRepo;
import com.example.spring17.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/spring17")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserRepo userRepo;


    @GetMapping("/error")
    public String error(){
        return "Error. Please contact administrator.";
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username){
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @GetMapping("/userid/{id}")
    public ResponseEntity<Optional<User>> getUsersById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
    }

    @DeleteMapping("user/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id){
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/user/update/{userid}")
    public ResponseEntity<Optional<User>> updateUser(@PathVariable("userid") Long id, @RequestBody User user){
        return ResponseEntity.ok().body(userService.updateUser(id, user));
    }

    @PutMapping("/user/update/{userid}/{password}")
    public ResponseEntity<Optional<User>> updatePassword(@RequestParam("userid") Long id,
                                                         @RequestParam("password") String password){
        return ResponseEntity.ok().body(userService.updatePassword(id, password));
    }

}
