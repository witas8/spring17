package com.example.spring17.model.curiosity.dto;

import com.example.spring17.model.user.dto.UserDTO;

import java.io.Serializable;

public record CuriosityFilterDTO (String username, String category,
                                  Boolean accepted, Integer likes) implements Serializable {

}