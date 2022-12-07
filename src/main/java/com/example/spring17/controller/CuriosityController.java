package com.example.spring17.controller;

import com.example.spring17.model.curiosity.dto.CuriosityDTO;
import com.example.spring17.model.curiosity.entity.Curiosity;
import com.example.spring17.service.curiosity.CuriosityServiceDeleter;
import com.example.spring17.service.curiosity.CuriosityServiceSelector;
import com.example.spring17.service.curiosity.CuriosityServiceSever;
import com.example.spring17.service.curiosity.CuriosityServiceUpdater;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spring17/curiosity")
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

    @GetMapping("/user/question/containing/{question}")
    public ResponseEntity<List<Curiosity>> getQuestionContaining(
            @PathVariable("question") String question){
        return ResponseEntity.ok().body(curiosityServiceSelector.getUserDTOByQuestionContaining(question));
    }

    @PostMapping("/save")
    public ResponseEntity<Curiosity> saveCuriosity(@RequestBody CuriosityDTO curiosityDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(curiosityServiceSever.saveCuriosity(curiosityDTO));
    }

}
