package com.example.spring17.model.user.dto;

import com.example.spring17.model.user.entity.Roles;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.lang.Nullable;
import java.io.Serializable;

@Builder
public record UserDTO(@Nullable Long id, String firstName, String lastName, String username,
                      String role, String email, String phone) implements Serializable {
}
