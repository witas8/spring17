package com.example.spring17.mapper;

import com.example.spring17.model.user.dto.UserDTO;
import com.example.spring17.model.user.dto.UserSaveDTO;
import com.example.spring17.model.user.entity.Roles;
import com.example.spring17.model.user.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public User mapUpdatedDtoToEntity(UserDTO userDTO, User user){
        return user.toBuilder()
                //.id(user.getId())
                .firstName(userDTO.firstName())
                .lastName(userDTO.lastName())
                .username(userDTO.username())
                //.password(user.getPassword())
                .role(Enum.valueOf(Roles.class, userDTO.role()))
                .email(userDTO.email())
                .phone(userDTO.phone())
                .build();

/*        return User.builder()
                .id(user.getId())
                .firstName(userDTO.firstName())
                .lastName(userDTO.lastName())
                .username(userDTO.username())
                .password(user.getPassword())
                .role(Enum.valueOf(Roles.class, userDTO.role()))
                .email(userDTO.email())
                .phone(userDTO.phone())
                .build();*/
    }

    public User mapSaveDtoToEntity(UserSaveDTO userSaveDTO, PasswordEncoder passwordEncoder){
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
