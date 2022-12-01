package com.example.spring17.controller;


import com.example.spring17.model.user.dto.UpdatePasswordDTO;
import com.example.spring17.model.user.dto.UserDTO;
import com.example.spring17.model.user.entity.User;
import com.example.spring17.service.user.UserServiceDeleter;
import com.example.spring17.service.user.UserServiceSaver;
import com.example.spring17.service.user.UserServiceSelector;
import com.example.spring17.service.user.UserServiceUpdater;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/spring17/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceSelector userServiceSelector;
    private final UserServiceSaver userServiceSaver;
    private final UserServiceUpdater userServiceUpdater;
    private final UserServiceDeleter userServiceDeleter;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getUsers(){
        return ResponseEntity.ok().body(userServiceSelector.getAllUsers());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<UserDTO>> getUsersById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(userServiceSelector.getUserById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable("username") String username){
        return ResponseEntity.ok().body(userServiceSelector.getUser(username));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable("email") String email){
        return ResponseEntity.ok().body(userServiceSelector.getUserByEmail(email));
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userServiceSaver.saveUser(userDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO){
        return ResponseEntity.ok().body(userServiceUpdater.updateUser(id, userDTO));
    }

    //TODO: create login functionality with proper user updating
    @PutMapping("/{userid}/password")
    public ResponseEntity<Optional<UserDTO>> updatePassword(@RequestParam("userid") Long id,
                                                         @RequestBody UpdatePasswordDTO updatePasswordDTO){
        return ResponseEntity.ok().body(userServiceUpdater.updatePassword(id, updatePasswordDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id){
        userServiceDeleter.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
