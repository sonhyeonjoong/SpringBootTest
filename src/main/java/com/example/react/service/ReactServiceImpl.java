package com.example.react.service;

import com.example.club.dto.NoteDTO;
import com.example.club.entity.Note;
import com.example.club.repository.NoteRepository;
import com.example.react.dto.MemberDTO;
import com.example.react.entity.ReactMember;
import com.example.react.repository.ReactMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReactServiceImpl implements ReactService {

    private final ReactMemberRepository reactMemberRepository;


    @Override
    public Long register(MemberDTO memberDTO) {

        ReactMember member = dtoToEntity(memberDTO);

        log.info("==========================");
        log.info(member);

        reactMemberRepository.save(member);

        return member.getNum();
    }

}
