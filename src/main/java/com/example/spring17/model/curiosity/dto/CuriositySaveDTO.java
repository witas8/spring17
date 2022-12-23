package com.example.spring17.model.curiosity.dto;

import com.example.spring17.model.user.dto.UserDTO;
import lombok.Builder;

import java.io.Serializable;


@Builder(toBuilder = true)
public record CuriositySaveDTO(UserDTO userDTO, String category, String question, String answer,
                               boolean accepted, int likes) implements Serializable {
}
