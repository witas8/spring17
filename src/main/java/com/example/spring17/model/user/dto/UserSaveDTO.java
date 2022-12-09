package com.example.spring17.model.user.dto;

import lombok.Builder;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.io.Serializable;

@Builder
public record UserSaveDTO(String firstName, String lastName, String username, String password,
                          String role, String email, String phone) implements Serializable {
}
