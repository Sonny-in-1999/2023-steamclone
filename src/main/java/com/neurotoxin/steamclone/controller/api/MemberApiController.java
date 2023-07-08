package com.neurotoxin.steamclone.controller.api;

import com.neurotoxin.steamclone.entity.single.Member;
import com.neurotoxin.steamclone.service.single.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;


    @GetMapping("/api/test/memberList")
    public List<Member> members() {
        return memberService.findAllMembers();
    }

    @GetMapping("/api/test/member/{memberId}")
    public Member findMemberById(@PathVariable Long memberId) {
        return memberService.findMemberById(memberId);
    }

    @GetMapping("/api/test/member/loginName/{loginName}")
    public Member findMemberByLoginName(@PathVariable String loginName) {
        return memberService.findMemberByLoginName(loginName);
    }

    @PostMapping("/api/test/member")
    public Member joinMember(@RequestBody Member member) {
        return memberService.register(member);
    }

    @DeleteMapping("/api/test/member/{memberId}")
    public void deleteMember(@PathVariable Long memberId) {
        memberService.delete(memberId);
    }

    @PutMapping("/api/test/member/{memberId}")
    public void updateMember(@PathVariable Long memberId, @RequestBody Member member) {
        memberService.update(memberId, member);
    }
}
