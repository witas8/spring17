package com.example.spring17.model.curiosity.dto;

import com.example.spring17.model.user.dto.UserDTO;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Optional;

public record CuriosityFilterDTO (String username, String category,
                                  String questionPart, boolean onlyAccepted) implements Serializable {

}