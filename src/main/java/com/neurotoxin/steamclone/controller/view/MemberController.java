package com.neurotoxin.steamclone.controller.view;

import com.neurotoxin.steamclone.entity.single.Member;
import com.neurotoxin.steamclone.service.single.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    // 일반유저 회원가입 페이지
    @GetMapping("/join")
    public String userJoinPage() {
        return "member/member_join";
    }

    // 회원가입 완료
    @PostMapping("/join")
    public String joinUser(@ModelAttribute Member member) {
        memberService.registerUser(member);
        return "redirect:/join-success";
    }

    // 개발자 회원가입 페이지
    @GetMapping("/dev")
    public String devJoinPage() {
        return "member/dev_join";
    }

    // 개발자 회원가입
    @PostMapping("/dev")
    public String joinDev(@ModelAttribute Member member) {
        memberService.registerDev(member);
        return "redirect:/join-success";
    }



    // 회원가입 완료 페이지
    @GetMapping("/join-success")
    public String joinSuccessPage() {
        return "member/member_join_success";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    // 유저 개인 페이지
    @GetMapping("/account/{memberId}")
    public String memberAccountPage(@PathVariable Long memberId, Model model) {
        Member member = memberService.findMemberById(memberId);
        model.addAttribute("member", member);
        return "member/member_account";
    }

    // 유저 개인 정보 수정 페이지
    @GetMapping("/account/edit/{memberId}")
    public String editMemberAccountPage(@PathVariable Long memberId, Model model) {
        Member member = memberService.findMemberById(memberId);
        model.addAttribute("member", member);
        return "member/member_account_edit";
    }

    // 유저 개인 정보 수정
    @PostMapping("account/edit/{memberId}")
    public String updateAccountPage(@PathVariable Long memberId, @ModelAttribute Member updatedMember) {
        memberService.update(memberId, updatedMember);
        return "redirect:/account/{memberId}";
    }

    // 유저 개인 페이지에서 회원탈퇴 페이지로 이동
    @GetMapping("account/leave/{memberId}")
    public String deleteMemberPage(@PathVariable Long memberId, Model model) {
        Member member = memberService.findMemberById(memberId);
        model.addAttribute("member", member);
        return "member/member_account_delete";
    }

    // 회원탈퇴 후 goodbye.html로 연결
    @PostMapping("/account/leave/{memberId}")
    public String deleteMember(@PathVariable Long memberId, Model model) {
        Member deletedMember = memberService.delete(memberId);
        model.addAttribute("member", deletedMember);
        return "member/goodbye";
    }

    // 유저검색 페이지
    @GetMapping("/search/member")
    public String nickNameSearchPage(@RequestParam(required = false) String nickName, Model model) {
        List<Member> members = memberService.findMemberByNickNameContaining(nickName);
        model.addAttribute("members", members);
        return "member/member_search";
    }

    // 검색을 통해 찾은 유저의 프로필 페이지
    @GetMapping("/profile/{memberId}")
    public String memberProfilePage(@PathVariable Long memberId, Model model) {
        Member member = memberService.findMemberById(memberId);
        model.addAttribute("member", member);
        return "member/member_profile";
    }
}
