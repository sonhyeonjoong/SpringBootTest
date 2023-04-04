package com.example.react.service;

import com.example.react.dto.MemberDTO;
import com.example.react.entity.ReactMember;

import java.util.List;

public interface ReactService {

    Long register(MemberDTO memberDTO);

    default ReactMember dtoToEntity(MemberDTO memberDTO){

        ReactMember member = ReactMember.builder()
                .num(memberDTO.getNum())
                .email(memberDTO.getEmail())
                .name(memberDTO.getName())
                .message(memberDTO.getMessage())
                .build();

        return member;
    }

    default MemberDTO entityToDTO(ReactMember member){

        MemberDTO memberDTO = MemberDTO.builder()
                .num(member.getNum())
                .email(member.getEmail())
                .name(member.getName())
                .message(member.getMessage())
                .regDate(member.getRegDate())
                .modDate(member.getModDate())
                .build();

        return memberDTO;

    }
}
