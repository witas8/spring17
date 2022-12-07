package com.example.spring17.service.curiosity;

import com.example.spring17.exceptions.BadRequestException;
import com.example.spring17.exceptions.NotFoundException;
import com.example.spring17.mapper.CuriosityMapper;
import com.example.spring17.model.curiosity.dto.CuriosityDTO;
import com.example.spring17.model.curiosity.entity.Curiosity;
import com.example.spring17.model.curiosity.user.entity.User;
import com.example.spring17.repository.CuriosityRepo;
import com.example.spring17.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CuriosityServiceSever {

    private final CuriosityRepo curiosityRepo;

    private final UserRepo userRepo;
    private final CuriosityMapper curiosityMapper;

    public Curiosity saveCuriosity(CuriosityDTO curiosityDTO) {
        Boolean doesQuestionExist = curiosityRepo.checkQuestionUniqueness(curiosityDTO.question());
        if (doesQuestionExist) {
            throw new BadRequestException("Question ", curiosityDTO.question(), true);
        }

        log.info("Saving new Curiosity from category {} and id {} to the database",
                curiosityDTO.category(), curiosityDTO.id());

        assert curiosityDTO.userDTO().id() != null;
        User user = userRepo.findById( curiosityDTO.userDTO().id())
                .orElseThrow(() -> new NotFoundException("User", "id", curiosityDTO.userDTO().id().toString()));

        return curiosityRepo.save(curiosityMapper.mapDtoToEntity(curiosityDTO, user));

    }

}
