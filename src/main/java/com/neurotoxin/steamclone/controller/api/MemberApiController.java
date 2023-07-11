package com.neurotoxin.steamclone.controller.api;

import com.neurotoxin.steamclone.entity.single.Member;
import com.neurotoxin.steamclone.service.single.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;


    // 전체 유저 목록 조회
    @GetMapping("/memberList")
    public List<Member> members() {
        return memberService.findAllMembers();
    }

    // 식별자를 이용한 멤버 조회
    @GetMapping("/memberId/{memberId}")
    public Member findMemberById(@PathVariable Long memberId) {
        return memberService.findMemberById(memberId);
    }

    // 로그인 아이디를 이용한 멤버 조회
    @GetMapping("/loginName/{loginName}")
    public Member findMemberByLoginName(@PathVariable String loginName) {
        return memberService.findMemberByLoginName(loginName);
    }

    // 닉네임을 이용한 멤버 조회
    @GetMapping("/nickName/{nickName}")
    public List<Member> findMemberByNickName(@PathVariable String nickName) {
        return memberService.findMemberByNickName(nickName);
    }



    // 회원탈퇴(멤버삭제)
    @DeleteMapping("/{memberId}")
    public void deleteMember(@PathVariable Long memberId) {
        memberService.delete(memberId);
    }

    // 회원정보 변경(멤버수정)
    @PutMapping("/{memberId}")
    public void updateMember(@PathVariable Long memberId, @RequestBody Member member) {
        memberService.update(memberId, member);
    }
}
