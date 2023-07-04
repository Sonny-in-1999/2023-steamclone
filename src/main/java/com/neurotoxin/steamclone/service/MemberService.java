package com.neurotoxin.steamclone.service;


import com.neurotoxin.steamclone.Entity.Game;
import com.neurotoxin.steamclone.Entity.Member;
import com.neurotoxin.steamclone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    // 유저 생성 메서드(회원 가입)
    @Transactional
    public Member create(Member member) {
        validateDuplicateMember(member);
        member.setLibrary(null);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByLoginName(member.getLoginName());
        if (findMember != null) {
            throw new IllegalStateException("이미 등록된 아이디 입니다.");
        }
    }

    // 유저 전건 조회
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    // 유저 단건 조회(id로 검색)
    public Member findMemberById(Long memberId) {
        return memberRepository.findMemberById(memberId);
    }

    // 유저 단건 조회(이름으로 검색)
    public Member findMemberByLoginName(String loginName) {
        return memberRepository.findByLoginName(loginName);
    }

    // 유저 삭제(회원 탈퇴)
    @Transactional
    public void delete(Member member) {
        validateMember(member);
        memberRepository.delete(member);
    }

    private void validateMember(Member member) {
        Member findMember = memberRepository.findMemberById(member.getId());
        if (findMember == null) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
    }

    // 유저 정보 수정
    @Transactional
    public void update(Long memberId, Member newMember) {
        Member oldMember = memberRepository.findMemberById(memberId);

        oldMember.setEmail(newMember.getEmail());
        oldMember.setNickName(newMember.getNickName());
        oldMember.setPassword(newMember.getPassword());
        oldMember.setPhoneNumber(newMember.getPhoneNumber());

        memberRepository.save(oldMember);
    }
}
