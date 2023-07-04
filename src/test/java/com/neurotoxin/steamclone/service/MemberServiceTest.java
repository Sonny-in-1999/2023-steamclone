package com.neurotoxin.steamclone.service;


import com.neurotoxin.steamclone.Entity.Grade;
import com.neurotoxin.steamclone.Entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberServiceTest {


    @Autowired
    MemberService memberService;


    @Test
    @DisplayName("회원 생성")
    @Transactional
    public void createMember() throws Exception {
        //given
        Member member1 = new Member();
        member1.setEmail("example1@gmail.com");
        member1.setLoginName("login-example1");
        member1.setPassword("password123");
        member1.setPhoneNumber("010-1234-5678");
        member1.setGrade(Grade.DEVELOPER);

        Member member2 = new Member();
        member2.setEmail("example2@gmail.com");
        member2.setLoginName("login-example2");
        member2.setPassword("password123");
        member2.setPhoneNumber("010-1234-5678");
        member2.setGrade(Grade.USER);

        //when
        memberService.create(member1);
        memberService.create(member2);
        //then
        assertThat(member1).isEqualTo(memberService.findMemberById(member1.getId()));
        assertThat(member2).isEqualTo(memberService.findMemberById(member2.getId()));
    }

    @Test
    @DisplayName("중복 회원 검증")
    public void validateDupMember() throws Exception {
        //given
        Member member1 = new Member();
        member1.setLoginName("login-example1");

        Member member2 = new Member();
        member2.setLoginName("login-example1");
        //when
        memberService.create(member1);
        //then
        assertThrows(IllegalStateException.class, () ->
                memberService.create(member2));
    }

    @Test
    @DisplayName("회원 탈퇴")
    public void memberDelete() throws Exception {

        Member member = new Member();
        member.setNickName("nickname1");
        member.setLoginName("loginName1");

        memberService.create(member);
        List<Member> allMembers = memberService.findAllMembers();
        assertThat(allMembers.size()).isEqualTo(1);

        memberService.delete(member);
        List<Member> allMembersAfterDelete = memberService.findAllMembers();
        assertThat(allMembersAfterDelete).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 회원 검증")
    public void unknownMember() throws Exception {
        //given
        Member member = new Member();
        member.setLoginName("member");
        member.setEmail("email@gmail.com");

        //when

        //then
        assertThrows(IllegalStateException.class, () ->
                memberService.findMemberById(2L));
    }
}
