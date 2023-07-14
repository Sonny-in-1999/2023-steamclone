package com.neurotoxin.steamclone.controller.view;

import com.neurotoxin.steamclone.entity.single.Member;
import com.neurotoxin.steamclone.service.single.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping(value = {"", "/"})
    public String home(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Member member = memberService.findMemberByLoginName(authentication.getName());
            model.addAttribute("member", member);

        }
        return "home";
    }
}
