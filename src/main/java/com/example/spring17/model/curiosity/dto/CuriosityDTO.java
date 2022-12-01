package com.example.spring17.model.curiosity.dto;

import com.example.spring17.model.curiosity.entity.Categories;
import com.example.spring17.model.user.dto.UserDTO;
import java.io.Serializable;

public record CuriosityDTO(Long id, UserDTO userDTO, String category, String question,
                           String answer, boolean accepted, int likes) implements Serializable {
}
