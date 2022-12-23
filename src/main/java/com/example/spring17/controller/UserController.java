package com.example.spring17.controller;


import com.example.spring17.model.user.dto.UpdatePasswordDTO;
import com.example.spring17.model.user.dto.UserDTO;
import com.example.spring17.model.user.dto.UserSaveDTO;
import com.example.spring17.model.user.entity.User;
import com.example.spring17.service.user.UserServiceDeleter;
import com.example.spring17.service.user.UserServiceSaver;
import com.example.spring17.service.user.UserServiceSelector;
import com.example.spring17.service.user.UserServiceUpdater;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.spring17.utils.ConstantURL.USER_URL;

@RestController
@RequestMapping(USER_URL)
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

    @GetMapping("/pagination/{page}/{limit}/{param}/{isAsc}")
    private ResponseEntity<Page<UserDTO>> getUserWithPagination(@PathVariable("page") int page,
                                                                @PathVariable("limit") int limit,
                                                                @PathVariable("param") String param,
                                                                @PathVariable("isAsc") Boolean isAscending) {
        return ResponseEntity.ok().body(userServiceSelector.getAllUsersWithPagination(page, limit, param, isAscending));
    }

    @GetMapping("/id/{id}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="JWT") })
    public ResponseEntity<UserDTO> getUsersById(@PathVariable("id") Long id){
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
    public ResponseEntity<User> createUser(@RequestBody UserSaveDTO userSaveDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userServiceSaver.saveUser(userSaveDTO));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO){
        return ResponseEntity.ok().body(userServiceUpdater.updateUser(id, userDTO));
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<Optional<UserDTO>> updatePassword(@PathVariable("id") Long id,
                                                         @RequestBody UpdatePasswordDTO updatePasswordDTO){
        return ResponseEntity.ok().body(userServiceUpdater.updatePassword(id, updatePasswordDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id){
        userServiceDeleter.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
