package com.example.spring17.controller;


import com.example.spring17.model.User;
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

    @GetMapping("/error")
    public String error(){
        return "Error. Please contact administrator.";
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username){
        return ResponseEntity.ok().body(userService.getUser(username));
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

    @DeleteMapping("user/delete")
    public ResponseEntity<?> deleteUser(@RequestBody User user){
        userService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*@PutMapping("/update/email")
    public ResponseEntity<Optional<User>> updateUserEmail(@RequestBody User user){
        return ResponseEntity.ok().body(userService.updateUserEmail(user));
    }*/

}
