package com.example.spring17.service.curiosity;

import com.example.spring17.exceptions.NotFoundException;
import com.example.spring17.mapper.CuriosityMapper;
import com.example.spring17.mapper.UserMapper;
import com.example.spring17.model.curiosity.dto.CuriosityDTO;
import com.example.spring17.model.curiosity.dto.CuriosityFilterDTO;
import com.example.spring17.model.curiosity.entity.Categories;
import com.example.spring17.model.curiosity.entity.Curiosity;
import com.example.spring17.model.user.entity.User;
import com.example.spring17.repository.CuriosityRepo;
import com.example.spring17.repository.UserRepo;
import com.example.spring17.service.user.UserServiceSelector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
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

    private final UserMapper userMapper;


    private final UserRepo userRepo;


    public List<CuriosityDTO> getAllCuriosities() {
        return curiosityRepo.findAll().stream().map(curiosityMapper::mapCuriosityToDTO).collect(Collectors.toList());
    }

    public Page<CuriosityDTO> getAllWithPagination(int page, int limit, String sortParam, boolean isAscending,
                                                   CuriosityFilterDTO curiosityFilterDTO){
        Curiosity curiosity = new Curiosity();
        if(curiosityFilterDTO.username() != null) curiosity.setUser(userRepo.findByUsername(curiosityFilterDTO.username())
                .orElseThrow(() -> new NotFoundException("User", "username", curiosityFilterDTO.username())));
        if(curiosityFilterDTO.category() != null) curiosity.setCategory(Categories.valueOf(curiosityFilterDTO.category()));
        if(curiosityFilterDTO.accepted() != null) curiosity.setAccepted(curiosityFilterDTO.accepted());
        if(curiosityFilterDTO.likes() != null) curiosity.setLikes(curiosityFilterDTO.likes());
        Example<Curiosity> curiosityExample = Example.of(curiosity);

        return curiosityRepo.findAll(
                curiosityExample,
                        PageRequest.of(page, limit).withSort(Sort.by(
                                isAscending ? Sort.Direction.ASC : Sort.Direction.DESC,
                                sortParam))
                        )
                .map(curiosityMapper::mapCuriosityToDTO);
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
