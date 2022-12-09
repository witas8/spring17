package com.example.spring17.service.curiosity;

import com.example.spring17.exceptions.NotFoundException;
import com.example.spring17.mapper.CuriosityMapper;
import com.example.spring17.model.curiosity.dto.CuriosityDTO;
import com.example.spring17.model.curiosity.entity.Curiosity;
import com.example.spring17.model.user.dto.UserDTO;
import com.example.spring17.repository.CuriosityRepo;
import com.example.spring17.service.user.UserServiceSelector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CuriosityServiceSelector {

    private final CuriosityRepo curiosityRepo;
    private final UserServiceSelector userServiceSelector;
    private final CuriosityMapper curiosityMapper;


    public List<CuriosityDTO> getAllCuriosities() {
        return curiosityRepo.findAll().stream().map(curiosityMapper::mapCuriosityToDTO).collect(Collectors.toList());
    }

    public Page<CuriosityDTO> getAllCuriositiesWithPagination(int page, int limit){
        return curiosityRepo.findAll(PageRequest.of(page, limit).withSort(Sort.by("id")))
                .map(curiosityMapper::mapCuriosityToDTO); //.withSort(Sort.by("username"))); // //Page<User> users
    }

    public Optional<Curiosity> getCuriosityById(Long id) {
        if(!curiosityRepo.existsById(id)) {
            throw new NotFoundException("Curiosity", "id", id.toString());
        }

        return curiosityRepo.findById(id);
    }

    public Optional<Curiosity> getByCategory(String category) {
        return curiosityRepo.findAll()
                .stream()
                .filter(i->i.getCategory().equals(category))
                .findAny();
    }


    public Optional<Curiosity> getByUsername(String username) {
        return curiosityRepo.findAll()
                .stream()
                .filter(i->i.getUser().getUsername().equals(username))
                .findAny();
    }

    public List<Curiosity> getAllAccepted() {
        return curiosityRepo.findAccepted();
    }

    public List<Curiosity> getAllNotAccepted() {
        return curiosityRepo.findNotAccepted();
    }

    public List<Curiosity> getOrderedByLikes(String param) {
        return curiosityRepo.filerByLikes(param);
    }

    public List<Curiosity> getUserDTOByQuestionContaining(String question) {
        return curiosityRepo.findByQuestionContaining(question)
                .orElseThrow(() -> new NotFoundException("Curiosity", "question", question));
    }

}
