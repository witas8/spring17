package com.example.spring17.service.curiosity;

import com.example.spring17.exceptions.NotFoundException;
import com.example.spring17.model.curiosity.entity.Curiosity;
import com.example.spring17.repository.CuriosityRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CuriosityServiceUpdater {

    private final CuriosityRepo curiosityRepo;

    public Optional<Curiosity> updateCuriosity(Long id, Curiosity updateCuriosity) {
        Curiosity CuriosityToBeUpdated = curiosityRepo.findById(updateCuriosity.getId())
                .orElseThrow(() -> new NotFoundException("Curiosity", "id", id.toString()));
        if(CuriosityToBeUpdated != null) {
            if(updateCuriosity.getQuestion() != null && !CuriosityToBeUpdated.getQuestion().equals(updateCuriosity.getQuestion()))
                CuriosityToBeUpdated.setQuestion(updateCuriosity.getQuestion()); //zrób tak usera!

            if(updateCuriosity.getCategory() != null && !CuriosityToBeUpdated.getCategory().equals(updateCuriosity.getCategory()))
                CuriosityToBeUpdated.setCategory(updateCuriosity.getCategory());

            if(updateCuriosity.getAnswer() != null && !CuriosityToBeUpdated.getAnswer().equals(updateCuriosity.getAnswer()))
                CuriosityToBeUpdated.setAnswer(updateCuriosity.getAnswer());

            if(updateCuriosity.getLikes() != 0 && CuriosityToBeUpdated.getLikes() != updateCuriosity.getLikes())
                CuriosityToBeUpdated.setLikes(updateCuriosity.getLikes());

            if(CuriosityToBeUpdated.isAccepted() != updateCuriosity.isAccepted())
                CuriosityToBeUpdated.setAccepted(updateCuriosity.isAccepted());

            curiosityRepo.findById(id).ifPresent(curiosityRepo::save);
        }

        log.info("Curiosity with id {} has been updated ", id);
        return curiosityRepo.findById(id);
    }

}
