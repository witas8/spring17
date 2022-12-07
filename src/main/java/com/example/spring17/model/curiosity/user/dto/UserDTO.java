package com.example.spring17.model.curiosity.user.dto;

import lombok.Builder;
import org.springframework.lang.Nullable;
import java.io.Serializable;

@Builder
public record UserDTO(@Nullable Long id, String firstName, String lastName, String username,
                      String role, String email, String phone) implements Serializable {
}
