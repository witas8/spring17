package com.example.spring17.service.curiosity;

import com.example.spring17.exceptions.BadRequestException;
import com.example.spring17.exceptions.NotFoundException;
import com.example.spring17.mapper.CuriosityMapper;
import com.example.spring17.model.curiosity.dto.CuriosityDTO;
import com.example.spring17.model.curiosity.dto.CuriositySaveDTO;
import com.example.spring17.model.curiosity.entity.Curiosity;
import com.example.spring17.model.user.entity.User;
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

    public CuriosityDTO saveCuriosity(CuriositySaveDTO curiositySaveDTO) {
        Boolean doesQuestionExist = curiosityRepo.checkQuestionUniqueness(curiositySaveDTO.question());
        if (doesQuestionExist) {
            throw new BadRequestException("Question ", curiositySaveDTO.question(), true);
        }

        log.info("Saving new Curiosity from category {} and question {} to the database",
                curiositySaveDTO.category(), curiositySaveDTO.question());

        assert curiositySaveDTO.userDTO().id() != null;
        User user = userRepo.findById( curiositySaveDTO.userDTO().id())
                .orElseThrow(() -> new NotFoundException("User", "id", curiositySaveDTO.userDTO().id().toString()));

        Curiosity curiosity= curiosityRepo.save(curiosityMapper.mapDtoToEntity(curiositySaveDTO, user));
        log.info("curiosity ID: " + curiosity.getId());

        return curiosityMapper.mapIdToDTO(curiosity);

    }

}
