package com.example.spring17.service.curiosity;

import com.example.spring17.exceptions.NotFoundException;
import com.example.spring17.model.curiosity.entity.Curiosity;
import com.example.spring17.repository.CuriosityRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CuriosityServiceDeleter {

    private final CuriosityRepo curiosityRepo;

    public void deleteCuriosityById(Long id) {
        if(!curiosityRepo.existsById(id)) {
            throw new NotFoundException("Curiosity", "id", id.toString());
        }

        Curiosity Curiosity = curiosityRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Curiosity", "id", id.toString()));
        log.info("Curiosity with id {}, from category {}, and question {} is deleted from the database.",
                Curiosity.getId(), Curiosity.getCategory(), Curiosity.getQuestion());
        curiosityRepo.findById(id).ifPresent(curiosityRepo::delete);
    }

    public void deleteCuriosityByQuestion(String question) {
        Curiosity Curiosity = curiosityRepo.findAll()
                .stream()
                .filter(i -> i.getQuestion().equals(question))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Curiosity", "question", question));

        log.info("Curiosity with id {}, from category {}, and question {} is deleted from the database.",
                Curiosity.getId(), Curiosity.getCategory(), Curiosity.getQuestion());
        curiosityRepo.delete(Curiosity);
    }

}
