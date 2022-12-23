package com.example.spring17.model.curiosity.dto;

import com.example.spring17.model.user.dto.UserDTO;
import lombok.Builder;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Builder(toBuilder = true)
public record CuriosityDTO(Long id, UserDTO userDTO, String category, String question,
                           String answer, boolean accepted, int likes, OffsetDateTime date) implements Serializable {
}
