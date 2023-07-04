package com.neurotoxin.steamclone.service;


import com.neurotoxin.steamclone.Entity.Game;
import com.neurotoxin.steamclone.Entity.Member;
import com.neurotoxin.steamclone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 유저 생성 메서드(회원 가입)
    public Member create(Member member) {
        return memberRepository.save(member);
    }

    // 유저 전건 조회
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    // 유저 단건 조회(id로 검색)
    public Member findMemberById(Long memberId) {
        return memberRepository.findMemberById(memberId);
    }

    // 유저 삭제(회원 탈퇴)
    public void delete(Member member) {
        memberRepository.delete(member);
    }

    // 유저 정보 수정
    public void update(Member member) {
    }
}
