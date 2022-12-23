package com.example.spring17.validators;

import com.example.spring17.exceptions.BadRequestException;
import com.example.spring17.exceptions.NotFoundException;
import com.example.spring17.model.user.dto.UserDTO;
import com.example.spring17.model.user.dto.UserSaveDTO;
import com.example.spring17.model.user.entity.Roles;
import com.example.spring17.model.user.entity.User;
import com.example.spring17.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepo userRepo;

    public void validateBeforeSaving(UserSaveDTO userSaveDTO){
        validateUserExistence(userSaveDTO.username());
        validateName(userSaveDTO.firstName());
        validateName(userSaveDTO.lastName());
        validateEmailExistence(userSaveDTO.email());
        validateEmailFormat(userSaveDTO.email());
        validatePhoneNumberLength(userSaveDTO.phone());
        validateRole(userSaveDTO.role());
    }

    public void validateBeforeUpdating(UserDTO userDTO, Optional<User> user){
        user.ifPresent(u -> {
            if(!u.getUsername().equals(userDTO.username())) validateUserExistence(userDTO.username());
            if(!u.getUsername().equals(userDTO.username())) validateEmailExistence(userDTO.email());
        });

        validateName(userDTO.firstName());
        validateName(userDTO.lastName());
        validatePhoneNumberLength(userDTO.phone());
        validateRole(userDTO.role());
    }

    public void validateIfExists(Long id){
        if(!userRepo.existsById(id)) {
            throw new NotFoundException("User", "id", id.toString());
        }
    }

    public void validateUserExistence(String username){
        Boolean doesUsernameExist = userRepo.checkUsernameIfExists(username);
        if (doesUsernameExist) throw new BadRequestException("Username", username, true);
    }

    private void validateEmailExistence(String email){
        Boolean doesEmailExist = userRepo.checkEmailIfExists(email);
        if (doesEmailExist) throw new BadRequestException("Email", email, true);
    }

    private void validateEmailFormat(String email){
        if (!email.contains("@") || !email.contains("."))
            throw new BadRequestException("Email", email, true);
    }

    void validatePhoneNumberLength(String phoneNumber){
        if (!phoneNumber.chars().allMatch(Character::isDigit)
                || (!phoneNumber.contains("+") && (phoneNumber.length()!=9))
                || phoneNumber.contains("+") && (phoneNumber.length()!=12)){
            throw new BadRequestException("Phone number", phoneNumber, false);
        }
    }

    private void validateRole(String role){
        if(!EnumUtils.isValidEnum(Roles.class, role)) {
            throw new BadRequestException("Role", role, false);
        }
    }

    private void validateName(String name){
        Pattern p = Pattern.compile("\\b([A-Z]\\w*)\\b");
        Matcher matcher = p.matcher(name);
        if(name.length() < 2 || !matcher.matches()){
            throw new BadRequestException("Name", name, false);
        }
    }

}
