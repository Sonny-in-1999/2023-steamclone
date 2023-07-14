package com.neurotoxin.steamclone.controller.view;

import com.neurotoxin.steamclone.entity.single.Community;
import com.neurotoxin.steamclone.entity.single.Media;
import com.neurotoxin.steamclone.service.single.CommunityService;
import com.neurotoxin.steamclone.service.single.MediaService;
import com.neurotoxin.steamclone.service.single.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;
    private final MemberService memberService;
    private final MediaService mediaService;

    // 커뮤니티 홈페이지
    @GetMapping("/community")
    public String community(Model model) {
        List<Community> communities = communityService.findAllCommunities();
        model.addAttribute("community", communities);
        return "community/community_home";
    }
    // 커뮤니티 글 작성 페이지
    @GetMapping("/community/post")
    public String postCommunity(Model model) {
        model.addAttribute("community", new Community());
        return "community/community_add";
    }
    // 커뮤니티 글 작성
    @PreAuthorize("#loginName == authentication.principal.username")
    @PostMapping("/community/post")
    public String post(@ModelAttribute Community community, @RequestParam List<MultipartFile> files) throws IOException {
        List<Media> media = mediaService.storeFiles(files);
        communityService.post(community, media);
        return "redirect:/community/" + community.getId();
    }

    @GetMapping("/community/{communityId}")
    public String detail(@PathVariable Long communityId, Model model) {
        Community community = communityService.findCommunityById(communityId);
        model.addAttribute("community", community);
        return "community/community_detail";
    }
}
