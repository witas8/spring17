package com.example.spring17.mapper;

import com.example.spring17.model.curiosity.user.dto.UserDTO;
import com.example.spring17.model.curiosity.user.dto.UserSaveDTO;
import com.example.spring17.model.curiosity.user.entity.Roles;
import com.example.spring17.model.curiosity.user.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public User mapUpdatedDtoToEntity(UserDTO userDTO, User user){
        //return new User(user.getId(), userDTO.firstName(), userDTO.lastName(),
         //       userDTO.username(), user.getPassword(), userDTO.role(), userDTO.email(), userDTO.phone());
        return User.builder()
                .id(user.getId())
                .firstName(userDTO.firstName())
                .lastName(userDTO.lastName())
                .username(userDTO.username())
                .password(user.getPassword())
                .role(Enum.valueOf(Roles.class, userDTO.role()))
                .email(userDTO.email())
                .phone(userDTO.phone())
                .build();
    }

    public User mapSaveDtoToEntity(UserSaveDTO userSaveDTO, PasswordEncoder passwordEncoder){
        //return new User(userDTO.id(), userDTO.firstName(), userDTO.lastName(),
        //        userDTO.username(), userDTO.role(), userDTO.email(), userDTO.phone());
        return User.builder()
                .firstName(userSaveDTO.firstName())
                .lastName(userSaveDTO.lastName())
                .username(userSaveDTO.username())
                .password(passwordEncoder.encode(userSaveDTO.password()))
                .role(Enum.valueOf(Roles.class, userSaveDTO.role()))
                .email(userSaveDTO.email())
                .phone(userSaveDTO.phone())
                .build();
    }

    public User mapDtoToEntity(UserDTO userDTO){
        //return new User(userDTO.id(), userDTO.firstName(), userDTO.lastName(),
        //        userDTO.username(), userDTO.role(), userDTO.email(), userDTO.phone());
        return User.builder()
                .firstName(userDTO.firstName())
                .lastName(userDTO.lastName())
                .username(userDTO.username())
                .role(Enum.valueOf(Roles.class, userDTO.role()))
                .email(userDTO.email())
                .phone(userDTO.phone())
                .build();
    }

    public UserDTO mapUserToDTO(User user){
        //UserDTO.builder().id(user.getId()).build();
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getRole().toString(),
                user.getEmail(),
                user.getPhone());
    }
}
