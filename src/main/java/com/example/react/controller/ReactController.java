package com.example.react.controller;


import com.example.react.dto.MemberDTO;
import com.example.react.service.ReactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReactController {

    private final ReactService reactService;
    @PostMapping("/submit")
    public ResponseEntity<Long> register(@RequestBody MemberDTO memberDTO){
        log.info("-----------register-------------------------------");
        log.info(memberDTO);

        Long num = reactService.register(memberDTO);

        return new ResponseEntity<>(num, HttpStatus.OK);
    }
}
