package com.neurotoxin.steamclone.controller.view;

import com.neurotoxin.steamclone.entity.single.Member;
import com.neurotoxin.steamclone.service.single.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    // 일반유저 회원가입 페이지
    @GetMapping("/join")
    public String userJoinPage(Model model) {
        model.addAttribute("member", new Member());
        return "member/member_join";
    }

    // 회원가입 완료
    @PostMapping("/join")
    public String joinUser(@ModelAttribute Member member) {
        memberService.registerUser(member);
        return "redirect:/join-success";
    }

    // 개발자 회원가입 페이지
    @GetMapping("/join/dev")
    public String joinDevPage(Model model) {
        model.addAttribute("member", new Member());
        return "member/dev_join";
    }

    // 개발자 회원가입
    @PostMapping("/join/dev")
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
    public String loginPage() {
        return "member/login";
    }

    // 유저 개인 페이지
    @GetMapping("/account/{loginName}")
    @PreAuthorize("#loginName == authentication.principal.username")
    public String memberAccountPage(@PathVariable String loginName, Model model) {
        Member member = memberService.findMemberByLoginName(loginName);
        model.addAttribute("member", member);
        return "member/member_account";
    }

    // 유저 개인 정보 수정 페이지
    @GetMapping("/account/edit/{loginName}")
    @PreAuthorize("#loginName == authentication.principal.username")
    public String editMemberAccountPage(@PathVariable String loginName, Model model) {
        Member member = memberService.findMemberByLoginName(loginName);
        model.addAttribute("member", member);
        return "member/member_account_edit";
    }

    // 유저 개인 정보 수정
    @PostMapping("account/edit/{loginName}")
    @PreAuthorize("#loginName == authentication.principal.username")
    public String updateAccountPage(@PathVariable String loginName, @ModelAttribute Member updatedMember) {
        Member member = memberService.findMemberByLoginName(loginName);
        memberService.update(member.getId(), updatedMember);
        return "redirect:/account/{loginName}";
    }

    // 유저 개인 페이지에서 회원탈퇴 페이지로 이동
    @GetMapping("account/leave/{loginName}")
    public String deleteMemberPage(@PathVariable String loginName, Model model) {
        Member member = memberService.findMemberByLoginName(loginName);
        model.addAttribute("member", member);
        return "member/member_account_delete";
    }

    // 회원탈퇴 후 goodbye.html로 연결
    @PostMapping("/account/leave/{loginName}")
    @PreAuthorize("#loginName == authentication.principal.username")
    public String deleteMember(@PathVariable String loginName, Model model) {
        Member member = memberService.findMemberByLoginName(loginName);
        Member deletedMember = memberService.delete(member.getId());
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
