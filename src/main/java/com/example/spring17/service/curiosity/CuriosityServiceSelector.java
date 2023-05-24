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
import org.postgresql.core.Parser;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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
        if(curiosityFilterDTO.onlyAccepted()) curiosity.setAccepted(true);
        if(curiosityFilterDTO.username() != null) curiosity.setUser(userRepo.findByUsername(curiosityFilterDTO.username())
                .orElseThrow(() -> new NotFoundException("User", "username", curiosityFilterDTO.username())));
        if(curiosityFilterDTO.category() != null) curiosity.setCategory(Categories.valueOf(curiosityFilterDTO.category()));

        Example<Curiosity> curiosityExample = Example.of(curiosity);

//        return curiosityRepo.findAll(
//                curiosityExample,
//                        PageRequest.of(page, limit).withSort(Sort.by(
//                                isAscending ? Sort.Direction.ASC : Sort.Direction.DESC,
//                                sortParam))
//                )
//                .map(curiosityMapper::mapCuriosityToDTO);

        List<CuriosityDTO> curiosityList = curiosityRepo.findAll(curiosityExample,
                PageRequest.of(page, limit).withSort(Sort.by(
                                isAscending ? Sort.Direction.ASC : Sort.Direction.DESC,
                                sortParam)))
                .stream()
                .map(curiosityMapper::mapCuriosityToDTO)
                .toList();

        if(curiosityFilterDTO.questionPart() != null){
//            List<CuriosityDTO> curiosityList2 = curiosityList.stream()
//                    .filter(c -> c.question().contains(curiosityFilterDTO.questionPart()))
//                    .toList();
            return new PageImpl<>(curiosityList.stream()
                    .filter(c -> c.question().toLowerCase()
                            .contains(curiosityFilterDTO.questionPart().trim().toLowerCase()))
                    .toList());

//            return new PageImpl<>(curiosityList.stream()
//                    .filter(c -> c.question().contains(curiosityFilterDTO.questionPart()))
//                    .collect(Collectors.toList()));
        }

        return new PageImpl<>(curiosityList);
    }

    public List<CuriosityDTO> getLikedCuriosities(String likedCuriosityIds, int from, int end){ //Page<CuriosityDTO>
        List<Long> curiosityIdList = Arrays.stream(likedCuriosityIds.split(",")).map(Long::parseLong).toList();
        //int const_limit = 2;
        //int const_start = 1;
        int limit = Math.min(curiosityIdList.size(), end);
        int start = Math.min(from, limit);
        List<Long> limitedCuriosityIdList = curiosityIdList.subList(start, limit);
        List<CuriosityDTO> likedCuriosityList = new ArrayList<>();
        for (Long id: limitedCuriosityIdList) {
            likedCuriosityList.add(curiosityMapper
                    .mapCuriosityToDTO(curiosityRepo.findById(id)
                            .orElseThrow(() -> new NotFoundException("Curiosity", "id", id.toString()))));
        }

        //return new PageImpl<>(likedCuriosityList);
        //return new PageImpl<>(likedCuriosityList, Pageable.ofSize(1), 0);
        //Pageable pageable = PageRequest.of(0,1, (Sort.by(Sort.Direction.DESC, "id")));
        //Pageable pageable = PageRequest.of(0, 1).withSort(Sort.by(Sort.Direction.DESC, "id"));
        //return new PageImpl<>(likedCuriosityList, pageable, 1); //likedCuriosityList.size()

        //likedCuriosityList.sort(Comparator.comparing(CuriosityDTO::date).reversed());
        return likedCuriosityList;
    }

    public CuriosityDTO getCuriosityById(Long id) {
        return curiosityRepo.findById(id).map(curiosityMapper::mapCuriosityToDTO)
                .orElseThrow(() -> new NotFoundException("Curiosity", "id", id.toString()));
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

    public List<CuriosityDTO> getAllAccepted() {
        return curiosityRepo.findAccepted().stream()
                .map(curiosityMapper::mapCuriosityToDTO)
                .collect(Collectors.toList());
    }

    public List<CuriosityDTO> getAllNotAccepted() {
        return curiosityRepo.findNotAccepted().stream()
                .map(curiosityMapper::mapCuriosityToDTO)
                .collect(Collectors.toList());
    }

    public List<Curiosity> getOrderedByLikes(String param) {
        return curiosityRepo.filerByLikes(param);
    }

    public List<CuriosityDTO> getUserDTOByQuestionContaining(String question) {
//        return curiosityRepo.findByQuestionContaining(question)
//                .orElseThrow(() -> new NotFoundException("Curiosity", "question", question));
        return curiosityRepo.findByQuestionContaining(question)
                .stream()
                .map(curiosityMapper::mapCuriosityToDTO).collect(Collectors.toList());
    }

    public CuriosityDTO getQuestion(String question) {
        return curiosityRepo.findByQuestion(question)
                .map(curiosityMapper::mapCuriosityToDTO)
                .orElseThrow(() -> new NotFoundException("Curiosity", "question", question));
    }

}
