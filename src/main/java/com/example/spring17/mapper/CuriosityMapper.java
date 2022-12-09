package com.example.spring17.mapper;

import com.example.spring17.model.curiosity.dto.CuriosityDTO;
import com.example.spring17.model.curiosity.entity.Categories;
import com.example.spring17.model.curiosity.entity.Curiosity;
import com.example.spring17.model.user.dto.UserDTO;
import com.example.spring17.model.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CuriosityMapper {

    private final UserMapper userMapper;

    public Curiosity mapDtoToEntity(CuriosityDTO curiosityDTO, User user){
        return Curiosity.builder()
                .user(user)
                .category(Enum.valueOf(Categories.class, curiosityDTO.category()))
                .question(curiosityDTO.question())
                .answer(curiosityDTO.answer())
                .accepted(curiosityDTO.accepted())
                .likes(curiosityDTO.likes())
                .build();
    }

    public CuriosityDTO mapCuriosityToDTO(Curiosity curiosity){
        return new CuriosityDTO(
                curiosity.getId(),
                userMapper.mapUserToDTO(curiosity.getUser()),
                curiosity.getCategory().toString(),
                curiosity.getQuestion(),
                curiosity.getAnswer(),
                curiosity.isAccepted(),
                curiosity.getLikes()
                );
    }

    public CuriosityDTO mapIdToDTO(Long id, CuriosityDTO curiosityDTO){
        return new CuriosityDTO(
                id,
                curiosityDTO.userDTO(),
                curiosityDTO.category(),
                curiosityDTO.question(),
                curiosityDTO.answer(),
                curiosityDTO.accepted(),
                curiosityDTO.likes()
        );
    }
}
