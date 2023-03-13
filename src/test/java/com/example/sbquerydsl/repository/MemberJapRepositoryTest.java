package com.example.sbquerydsl.repository;


import com.example.sbquerydsl.dto.MemberTeamDto;
import com.example.sbquerydsl.dto.condition.MemberSearchCondition;
import com.example.sbquerydsl.entity.Member;
import com.example.sbquerydsl.entity.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberJapRepositoryTest {

   @Autowired
   EntityManager em;

   JPAQueryFactory queryFactory;

   @PersistenceUnit
   EntityManagerFactory emf;


   @BeforeEach
   public void before() {
      queryFactory = new JPAQueryFactory(em);

//        EntityTransaction transaction = em.getTransaction();
//        transaction.begin();


      Team teamA = new Team("teamA");
      Team teamB = new Team("teamB");
      em.persist(teamA);
      em.persist(teamB);

      Member member1 = new Member("member1", 10, teamA);
      Member member2 = new Member("member2", 20, teamA);

      Member member3 = new Member("member3", 30, teamB);
      Member member4 = new Member("member4", 40, teamB);

      em.persist(member1);
      em.persist(member2);
      em.persist(member3);
      em.persist(member4);

      //em.flush();
      // em.clear();
//        transaction.commit();
   }
   @Autowired
   MemberRepository memberRepository;

   @Test
   @Rollback(value = false)
    public void basicTest() {
       Member member = new Member("member1", 10);
       memberRepository.save(member);

       List<Member> result = memberRepository.findAll_Querydsl();
       assertThat(result).containsExactly(member);

       List<Member> result2 = memberRepository.findByUsername_Querydsl("member1");
       assertThat(result2).containsExactly(member);

   }

   @Test
   public void searchTest() {
      MemberSearchCondition condition = new MemberSearchCondition();
      condition.setAgeGoe(35);
      condition.setAgeLoe(40);
      condition.setTeamName("teamB");

      List<MemberTeamDto> result = memberRepository.search(condition);

      assertThat(result).extracting("username").containsExactly("member4");
   }

//   @Test
//   public void entityQLTest() {
//      Q<Member> member = qEntity(Member.class);
//
//
//   }
}
