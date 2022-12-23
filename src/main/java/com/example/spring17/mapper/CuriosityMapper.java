package com.example.spring17.mapper;

import com.example.spring17.model.curiosity.dto.CuriosityDTO;
import com.example.spring17.model.curiosity.dto.CuriositySaveDTO;
import com.example.spring17.model.curiosity.entity.Categories;
import com.example.spring17.model.curiosity.entity.Curiosity;
import com.example.spring17.model.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CuriosityMapper {

    private final UserMapper userMapper;

    public Curiosity mapDtoToEntity(CuriositySaveDTO curiositySaveDTO, User user){
        return Curiosity.builder()
                .user(user)
                .category(Enum.valueOf(Categories.class, curiositySaveDTO.category()))
                .question(curiositySaveDTO.question())
                .answer(curiositySaveDTO.answer())
                .accepted(curiositySaveDTO.accepted())
                .likes(curiositySaveDTO.likes())
                .build();
    }

    public CuriosityDTO mapCuriosityToDTO(Curiosity curiosity){
        return new CuriosityDTO(
                curiosity.getId(),
                userMapper.mapUserToDTO(curiosity.getUser()),
                curiosity.getCategory().toString(),
                curiosity.getQuestion(),
                curiosity.getAnswer(),
                curiosity.getAccepted(),
                curiosity.getLikes(),
                curiosity.getCreateDate()
                );
    }

    public CuriosityDTO mapIdToDTO(Curiosity curiosity){
        return CuriosityDTO.builder()
                .id(curiosity.getId())
                .userDTO(userMapper.mapUserToDTO(curiosity.getUser()))
                .category(curiosity.getCategory().toString())
                .question(curiosity.getQuestion())
                .answer(curiosity.getAnswer())
                .accepted(curiosity.getAccepted())
                .likes(curiosity.getLikes())
                .date(curiosity.getCreateDate())
                .build();
        /*return new CuriosityDTO(
                curiosity.getId(),
                curiosity.getUser(),
                curiosity.getCategory(),
                curiosity.getQuestion(),
                curiosity.getAnswer(),
                curiosity.isAccepted(),
                curiosity.getLikes(),
                curiosity.getDate()
        );*/
    }
}
