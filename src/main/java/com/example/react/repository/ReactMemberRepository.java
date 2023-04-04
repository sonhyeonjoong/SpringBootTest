package com.example.react.repository;

import com.example.club.entity.ClubMember;
import com.example.react.entity.ReactMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReactMemberRepository extends JpaRepository<ReactMember, String> {

}
