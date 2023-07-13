package com.neurotoxin.steamclone.controller.view;


import com.neurotoxin.steamclone.entity.single.Member;
import com.neurotoxin.steamclone.service.single.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;


    // *주의!: 어드민 계정 생성을 위한 임시 링크입니다. production 단계에서는 삭제하세요.
    @GetMapping("/join/admin")
    public String joinAdminPage(Model model) {
        model.addAttribute("member", new Member());
        return "member/admin_join";
    }

    @PostMapping("/join/admin")
    public String joinAdmin(@ModelAttribute Member member) {
        memberService.registerAdmin(member);
        return "redirect:/join-success";
    }

    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")
    public String adminPage() {
        return "*개발 예정: 어드민 페이지 입니다.";
    }
}
