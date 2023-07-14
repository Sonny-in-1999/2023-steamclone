package com.neurotoxin.steamclone.controller.view;


import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.entity.single.Member;
import com.neurotoxin.steamclone.service.single.GameService;
import com.neurotoxin.steamclone.service.single.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final GameService gameService;


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


    // 어드민 메인 페이지
    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")
    public String adminPage() {
        return "admin/admin_home";
    }

    // 어드민 게임 목록 페이지
    @GetMapping("/admin/game/list")
    @Secured("ROLE_ADMIN")
    public String adminGameListPage(Model model) {
        List<Game> games = gameService.findAllGames();
        model.addAttribute("games", games);
        return "admin/admin_game_list";
    }

    // 어드민 게임 삭제 페이지
    @GetMapping("/admin/game/delete/{gameId}")
    @Secured("ROLE_ADMIN")
    public String adminGameDeletePage(@PathVariable Long gameId, Model model) {
        Game game = gameService.findGameById(gameId);
        model.addAttribute("game", game);
        return "admin/admin_game_delete";
    }

    // 어드민 멤버 삭제
    @PostMapping("/admin/game/delete/{gameId}")
    @Secured("ROLE_ADMIN")
    public String adminGameDelete(@PathVariable Long gameId) {
        gameService.delete(gameId);
        return "redirect:/admin/game/list";
    }

    // 어드민 멤버 목록 페이지
    @GetMapping("/admin/member/list")
    @Secured("ROLE_ADMIN")
    public String adminMemberListPage(Model model) {
        List<Member> members = memberService.findAllMembers();
        model.addAttribute("members", members);
        return "admin/admin_member_list";
    }

    // 어드민 멤버 삭제 페이지
    @GetMapping("/admin/member/delete/{memberId}")
    @Secured("ROLE_ADMIN")
    public String adminMemberDeletePage(@PathVariable Long memberId, Model model) {
        Member member = memberService.findMemberById(memberId);
        model.addAttribute("member", member);
        return "admin/admin_member_delete";
    }

    // 어드민 멤버 삭제
    @PostMapping("/admin/member/delete/{memberId}")
    @Secured("ROLE_ADMIN")
    public String adminMemberDelete(@PathVariable Long memberId) {
        memberService.delete(memberId);
        return "redirect:/admin/member/list";
    }

}
