package com.example.spring17.controller;

import com.example.spring17.model.curiosity.dto.CuriosityDTO;
import com.example.spring17.model.curiosity.dto.CuriosityFilterDTO;
import com.example.spring17.model.curiosity.dto.CuriositySaveDTO;
import com.example.spring17.model.curiosity.entity.Curiosity;
import com.example.spring17.service.curiosity.CuriosityServiceDeleter;
import com.example.spring17.service.curiosity.CuriosityServiceSelector;
import com.example.spring17.service.curiosity.CuriosityServiceSever;
import com.example.spring17.service.curiosity.CuriosityServiceUpdater;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.spring17.utils.ConstantURL.CURIOSITY_URL;

@RestController
@RequestMapping(CURIOSITY_URL)
@AllArgsConstructor
@Slf4j
public class CuriosityController {

    private final CuriosityServiceSelector curiosityServiceSelector;
    private final CuriosityServiceSever curiosityServiceSever;
    private final CuriosityServiceUpdater curiosityServiceUpdater;
    private final CuriosityServiceDeleter curiosityServiceDeleter;

    @GetMapping("/all")
    public ResponseEntity<List<CuriosityDTO>> getCuriosities(){
        return ResponseEntity.ok().body(curiosityServiceSelector.getAllCuriosities());
    }

    @GetMapping("/id/{id}")
    private ResponseEntity<CuriosityDTO> getCuriosity(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(curiosityServiceSelector.getCuriosityById(id));
    }

    @GetMapping("/question/{question}")
    public ResponseEntity<List<CuriosityDTO>> getQuestionContaining(
            @PathVariable("question") String question){
        return ResponseEntity.ok().body(curiosityServiceSelector.getUserDTOByQuestionContaining(question));
    }

    @GetMapping("/question/whole/{question}")
    public ResponseEntity<CuriosityDTO> getQuestion(
            @PathVariable("question") String question){
        return ResponseEntity.ok().body(curiosityServiceSelector.getQuestion(question));
    }

    @GetMapping("/accepted")
    public ResponseEntity<List<CuriosityDTO>> getAllAccepted(){
        return ResponseEntity.ok().body(curiosityServiceSelector.getAllAccepted());
    }

    @GetMapping("/notaccepted")
    public ResponseEntity<List<CuriosityDTO>> getAllNotAccepted(){
        return ResponseEntity.ok().body(curiosityServiceSelector.getAllNotAccepted());
    }

    @PostMapping("/pagination/{page}/{limit}/{param}/{isAsc}")
    private ResponseEntity<Page<CuriosityDTO>> getCuriosityWithPagination(@PathVariable("page") int page,
                                                                          @PathVariable("limit") int limit,
                                                                          @PathVariable("param") String sortParam,
                                                                          @PathVariable("isAsc") boolean isAscending,
                                                                          @RequestBody CuriosityFilterDTO curiosityFilterDTO){
        return ResponseEntity.ok().body(curiosityServiceSelector
                .getAllWithPagination(page, limit, sortParam, isAscending, curiosityFilterDTO));
    }

    @PostMapping("/favourite/{ids}/{from}/{end}")
    //ResponseEntity<Page<CuriosityDTO>>
    public ResponseEntity<List<CuriosityDTO>> getLikedCuriosities(@PathVariable("ids") String likedCuriosityIds,
                                                                  @PathVariable("from") int from,
                                                                  @PathVariable("end") int end){
        return ResponseEntity.ok().body(curiosityServiceSelector.getLikedCuriosities(likedCuriosityIds, from, end));
    }

    @PostMapping("/save")
    public ResponseEntity<CuriosityDTO> saveCuriosity(@RequestBody CuriositySaveDTO curiositySaveDTO){ //CuriosityDTO curiosityDTO
        return ResponseEntity.status(HttpStatus.CREATED).body(curiosityServiceSever.saveCuriosity(curiositySaveDTO));
    }
}
