package com.example.spring17.model.user.dto;

import com.example.spring17.model.curiosity.entity.Curiosity;
import lombok.Builder;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;

@Builder
public record UserSaveDTO(String firstName, String lastName, String username, String password, String role,
                          String email, String phone, String likedCuriosityIDs) implements Serializable {
}
